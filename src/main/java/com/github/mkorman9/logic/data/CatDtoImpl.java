package com.github.mkorman9.logic.data;

public class CatDtoImpl implements CatDto {
    private Long id;
    private String roleName;
    private String name;
    private Integer duelsWon;
    private CatsGroupDto group;

    CatDtoImpl(Long id, String roleName, String name, Integer duelsWon, CatsGroupDto group) {
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
    public CatsGroupDto getGroup() {
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
        private CatsGroupDto group;

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

        public Builder withGroup(CatsGroupDto group) {
            this.group = group;
            return this;
        }

        public CatDtoImpl get() {
            return new CatDtoImpl(id, roleName, name, duelsWon, group);
        }
    }
}
