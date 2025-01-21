package com.cleartax.superhero.dto;

public class SuperheroRequestBody {
    private String name;
    private String power;
    private String universe;

    public SuperheroRequestBody() {
    }

    public String getName() {
        return this.name;
    }

    public String getPower() {
        return this.power;
    }

    public String getUniverse() {
        return this.universe;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPower(String power) {
        this.power = power;
    }

    public void setUniverse(String universe) {
        this.universe = universe;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof SuperheroRequestBody)) return false;
        final SuperheroRequestBody other = (SuperheroRequestBody) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$name = this.getName();
        final Object other$name = other.getName();
        if (this$name == null ? other$name != null : !this$name.equals(other$name)) return false;
        final Object this$power = this.getPower();
        final Object other$power = other.getPower();
        if (this$power == null ? other$power != null : !this$power.equals(other$power)) return false;
        final Object this$universe = this.getUniverse();
        final Object other$universe = other.getUniverse();
        if (this$universe == null ? other$universe != null : !this$universe.equals(other$universe)) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof SuperheroRequestBody;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $name = this.getName();
        result = result * PRIME + ($name == null ? 43 : $name.hashCode());
        final Object $power = this.getPower();
        result = result * PRIME + ($power == null ? 43 : $power.hashCode());
        final Object $universe = this.getUniverse();
        result = result * PRIME + ($universe == null ? 43 : $universe.hashCode());
        return result;
    }

    public String toString() {
        return "SuperheroRequestBody(name=" + this.getName() + ", power=" + this.getPower() + ", universe=" + this.getUniverse() + ")";
    }
}
