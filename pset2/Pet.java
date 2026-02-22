public abstract class Pet {
    protected String name;
    protected int year;

    public Pet(String name, int year) {
        this.name = name;
        this.year = year;
    }

    public String getName(){
        return this.name;
    }

    public abstract String speak();
}
