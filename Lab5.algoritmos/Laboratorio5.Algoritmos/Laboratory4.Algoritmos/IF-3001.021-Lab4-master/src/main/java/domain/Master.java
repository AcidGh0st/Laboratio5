package domain;

public abstract class Master {
    private int id;
    private String name;

    public Master(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    abstract String print();

    @Override
    public String toString() {
        return STR."id=\{id}, name=\{name},";
    }
}
