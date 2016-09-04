package com.github.mkorman9.logic.data;

public class CatsGroupDataOutput implements CatsGroupData {
    private Long id;
    private String name;

    CatsGroupDataOutput(Long id, String name) {
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

        public CatsGroupDataOutput get() {
            return new CatsGroupDataOutput(id, name);
        }
    }
}
