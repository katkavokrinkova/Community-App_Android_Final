package net.impacthub.members.model.members;

public class CombinedMemberSkill {

    private final Member memberDetail;
    private final Skill skill;

    private CombinedMemberSkill(Builder builder) {
        memberDetail = builder.memberDetail;
        skill = builder.skill;
    }

    public Member getMemberDetail() {
        return memberDetail;
    }

    public Skill getSkill() {
        return skill;
    }

    public static final class Builder {
        private Member memberDetail;
        private Skill skill;

        public Builder() {}

        public Builder memberDetail(Member memberDetail) {
            this.memberDetail = memberDetail;
            return this;
        }

        public Builder skill(Skill skill) {
            this.skill = skill;
            return this;
        }

        public CombinedMemberSkill build() {
            return new CombinedMemberSkill(this);
        }
    }
}
