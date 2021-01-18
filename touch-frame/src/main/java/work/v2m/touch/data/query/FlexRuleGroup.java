package work.v2m.touch.data.query;

import java.util.List;

/**
 * Created by <a href="mailto:88052350@qq.com">chengqiang.han</a> on 2021/1/19.
 */
public class FlexRuleGroup {
    public enum GroupOperator {
        AND, OR
    }
    private GroupOperator groupOp;
    private List<FlexRule> rules;
    private List<FlexRuleGroup> groups;

    public FlexRuleGroup() {
        this.groupOp = GroupOperator.AND;
    }

    public FlexRuleGroup(String groupOp) {
//        try {
            this.groupOp = Enum.valueOf(GroupOperator.class, groupOp);
//        } catch (Throwable e) {
//            throw new IllegalArgumentException("groupOp(" + groupOp + ") not support.", e);
//        }
    }



}
