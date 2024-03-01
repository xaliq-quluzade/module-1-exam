import java.util.List;

public class Main {
    public static void main(String[] args) throws FileIOException, InvalidDataException, ItemNotFoundException {
        InventoryManager manager = new InventoryManager();

        manager.addItem(new InventoryItem(1, "item1", 4, 5));
        manager.addItem(new InventoryItem(2, "item2", 3, 10));
        manager.addItem(new InventoryItem(3, "item3", 2, 15));
        manager.addItem(new InventoryItem(4, "item4", 1, 20));

        InventoryItem findItem = manager.findItem(2);

        findItem.setQuantity(1234);
        findItem.setName("tes123");
        manager.updateItem(findItem);

        manager.deleteItem(1);

        List<InventoryItem> items = manager.readInventoryItems();
        for (InventoryItem item : items) {
            System.out.println(item);
        }
    }
}
