class ProductOfNumbers {
    // Stores the prefix products. The first element is always 1.
    private List<Integer> prefixProducts;

    public ProductOfNumbers() {
        prefixProducts = new ArrayList<>();
        prefixProducts.add(1); // Initialize with 1 for easy multiplication
    }

    public void add(int num) {
        if (num == 0) {
            // If we encounter a 0, reset the prefix products.
            // Any product including this 0 will be 0.
            prefixProducts.clear();
            prefixProducts.add(1);
        } else {
            // Multiply the new number with the last prefix product and add to list
            int lastProduct = prefixProducts.get(prefixProducts.size() - 1);
            prefixProducts.add(lastProduct * num);
        }
    }

    public int getProduct(int k) {
        int size = prefixProducts.size();
        // If k is greater than or equal to the size of the prefix list,
        // it means the last k numbers include a 0, so the product is 0.
        if (k >= size) {
            return 0;
        }
        // The product of the last k numbers is the total product divided by
        // the product before these k numbers.
        return prefixProducts.get(size - 1) / prefixProducts.get(size - k - 1);
    }
}