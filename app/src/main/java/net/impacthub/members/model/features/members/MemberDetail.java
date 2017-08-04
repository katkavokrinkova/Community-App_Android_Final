package net.impacthub.members.model.members;

import java.util.List;

public class MemberDetail {

    private final List<Affiliation> groups;
    private final List<Affiliation> projects;
    private final List<CombinedMemberSkill> skills;

    private MemberDetail(Builder builder) {
        groups = builder.groups;
        projects = builder.projects;
        skills = builder.skills;
    }

    public List<Affiliation> getGroups() {
        return groups;
    }

    public List<Affiliation> getProjects() {
        return projects;
    }

    public List<CombinedMemberSkill> getSkills() {
        return skills;
    }

    public static final class Builder {
        private List<Affiliation> groups;
        private List<Affiliation> projects;
        private List<CombinedMemberSkill> skills;

        public Builder() {
        }

        public Builder groups(List<Affiliation> groups) {
            this.groups = groups;
            return this;
        }

        public Builder projects(List<Affiliation> projects) {
            this.projects = projects;
            return this;
        }

        public Builder skills(List<CombinedMemberSkill> skills) {
            this.skills = skills;
            return this;
        }

        public MemberDetail build() {
            return new MemberDetail(this);
        }
    }
}
