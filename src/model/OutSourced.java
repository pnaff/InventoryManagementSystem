package model;

/**
 * OutSourced class extension of part model.
 */
public class OutSourced extends Part {
    private String companyName;
    public OutSourced(int id, String name, double price, int stock, int min, int max, String companyName) {
        super(id, name, price, stock, min, max);
        this.companyName = companyName;
    }
    /**
     * Sets company name attribute.
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
    /**
     * Gets company name attribute.
     */
    public String getCompanyName() {
        return this.companyName;
    }
}