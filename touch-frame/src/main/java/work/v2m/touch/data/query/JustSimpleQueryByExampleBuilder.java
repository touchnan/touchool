package work.v2m.touch.data.query;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.mapping.SimplePropertyHandler;
import org.springframework.data.mapping.context.MappingContext;
import org.springframework.data.relational.core.mapping.RelationalPersistentEntity;
import org.springframework.data.relational.core.mapping.RelationalPersistentProperty;
import org.springframework.data.relational.core.query.Criteria;
import org.springframework.data.relational.core.query.Query;
import org.springframework.data.support.ExampleMatcherAccessor;
import org.springframework.data.util.DirectFieldAccessFallbackBeanWrapper;
import org.springframework.lang.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * Created by <a href="mailto:88052350@qq.com">chengqiang.han</a> on 2021/1/18.
 * just simple,to refactor when need
 * reference from
 *      org.springframework.data.jpa.repository.support.ExampleSpecification
 *      org.springframework.data.jpa.convert.QueryByExamplePredicateBuilder
 */
public class JustSimpleQueryByExampleBuilder {
    private static final char escapeCharacter = '\\';
    private static final List<String> TO_REPLACE = Arrays.asList("_", "%");

    public static Query build(MappingContext<? extends RelationalPersistentEntity<?>, ? extends RelationalPersistentProperty> mappingContext,
                              Example<?> of) {
        return  build(mappingContext,of.getProbeType(), of.getProbe(),new ExampleMatcherAccessor(of.getMatcher()));
    }

    public static Query build(MappingContext<? extends RelationalPersistentEntity<?>, ? extends RelationalPersistentProperty> mappingContext,
                              Class<?> domainClass, Example<?> of) {
        return  build(mappingContext,domainClass, of.getProbe(),new ExampleMatcherAccessor(of.getMatcher()));
    }
    public static Query build(MappingContext<? extends RelationalPersistentEntity<?>, ? extends RelationalPersistentProperty> mappingContext,
                              Class<?> domainClass, Object value, ExampleMatcherAccessor exampleAccessor) {
        RelationalPersistentEntity<?> persistentEntity = mappingContext.getPersistentEntity(domainClass);
        return  build(persistentEntity,value,exampleAccessor);
    }

    public static Query build(RelationalPersistentEntity<?> persistentEntity,
                               Object value,ExampleMatcherAccessor exampleAccessor) {
        DirectFieldAccessFallbackBeanWrapper beanWrapper = new DirectFieldAccessFallbackBeanWrapper(value);
        List<Criteria> criteriaList = new ArrayList<>();
        persistentEntity.doWithProperties((SimplePropertyHandler) prop -> {
            if (!exampleAccessor.isIgnoredPath(prop.getName())) {
                ExampleMatcher.PropertyValueTransformer transformer = exampleAccessor.getValueTransformerForPath(prop.getName());
                Optional<Object> optionalValue = transformer
                        .apply(Optional.ofNullable(beanWrapper.getPropertyValue(prop.getName())));
                if (!optionalValue.isPresent()) {
                    if (exampleAccessor.getNullHandler().equals(ExampleMatcher.NullHandler.INCLUDE)) {
                        criteriaList.add(Criteria.where(prop.getName()).isNotNull());
                    }
                } else {
                    Object attributeValue = optionalValue.get();
                    if (prop.getType().equals(String.class)) {
                        if (exampleAccessor.isIgnoreCaseForPath(prop.getName())) {
                            attributeValue = attributeValue.toString().toLowerCase();
                        }
                        switch (exampleAccessor.getStringMatcherForPath(prop.getName())) {
                            case DEFAULT:
                            case EXACT:
                                criteriaList.add(Criteria.where(prop.getName()).is(attributeValue));
                                break;
                            case CONTAINING:
                                //"%" + EscapeCharacter.DEFAULT.escape(attributeValue.toString()) + "%"
                                criteriaList.add(Criteria.where(prop.getName()).like("%"+escape(attributeValue.toString())+"%"));
                                break;
                            case STARTING:
                                //escapeCharacter.escape(attributeValue.toString()) + "%",
                                criteriaList.add(Criteria.where(prop.getName()).like(escape(attributeValue.toString())+"%"));
                                break;
                            case ENDING:
                                //"%" + escapeCharacter.escape(attributeValue.toString())
                                criteriaList.add(Criteria.where(prop.getName()).like("%"+escape(attributeValue.toString())));
                                break;
                            default:
                                throw new IllegalArgumentException(
                                        "Unsupported StringMatcher " + exampleAccessor.getStringMatcherForPath(prop.getName()));
                        }
                    } else {
                        criteriaList.add(Criteria.where(prop.getName()).is(attributeValue));
                    }
                }
            }

        });
        return Query.query(Criteria.from(criteriaList));
    }

    @Nullable
    private static String escape(@Nullable String value) {

        return value == null //
                ? null //
                : Stream.concat(Stream.of(String.valueOf(escapeCharacter)), TO_REPLACE.stream()) //
                .reduce(value, (it, character) -> it.replace(character, escapeCharacter + character));
    }
}
