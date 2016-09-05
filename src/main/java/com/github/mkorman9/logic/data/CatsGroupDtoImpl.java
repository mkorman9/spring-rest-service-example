package com.github.mkorman9.logic.data;

public class CatsGroupDtoImpl implements CatsGroupDto {
    private Long id;
    private String name;

    CatsGroupDtoImpl(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    public static Builder build() {
        return new Builder();
    }

    public static class Builder {
        private Long id;
        private String name;

        Builder() {
        }

        public Builder withId(Long id) {
            this.id = id;
            return this;
        }

        public Builder withName(String name) {
            this.name = name;
            return this;
        }

        public CatsGroupDtoImpl get() {
            return new CatsGroupDtoImpl(id, name);
        }
    }
}
