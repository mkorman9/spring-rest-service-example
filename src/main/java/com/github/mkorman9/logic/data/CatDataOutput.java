package com.github.mkorman9.logic.data;

public class CatDataOutput implements CatData {
    private Long id;
    private String roleName;
    private String name;
    private Integer duelsWon;
    private CatsGroupData group;

    CatDataOutput(Long id, String roleName, String name, Integer duelsWon, CatsGroupData group) {
        this.id = id;
        this.roleName = roleName;
        this.name = name;
        this.duelsWon = duelsWon;
        this.group = group;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public String getRoleName() {
        return roleName;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Integer getDuelsWon() {
        return duelsWon;
    }

    @Override
    public CatsGroupData getGroup() {
        return group;
    }

    public static Builder build() {
        return new Builder();
    }

    public static class Builder {
        private Long id;
        private String roleName;
        private String name;
        private Integer duelsWon;
        private CatsGroupData group;

        Builder() {
        }

        public Builder withId(Long id) {
            this.id = id;
            return this;
        }

        public Builder withRoleName(String roleName) {
            this.roleName = roleName;
            return this;
        }

        public Builder withName(String name) {
            this.name = name;
            return this;
        }

        public Builder withDuelsWon(Integer duelsWon) {
            this.duelsWon = duelsWon;
            return this;
        }

        public Builder withGroup(CatsGroupData group) {
            this.group = group;
            return this;
        }

        public CatDataOutput get() {
            return new CatDataOutput(id, roleName, name, duelsWon, group);
        }
    }
}
