import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class InventoryManager {
    private static final String FILE_NAME = "inventory.txt";

    public List<InventoryItem> readInventoryItems() throws FileIOException {
        List<InventoryItem> items = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null && !line.isEmpty()) {
                String[] parts = line.split(",");
                int itemId = Integer.parseInt(parts[0]);
                String name = parts[1];
                int quantity = Integer.parseInt(parts[2]);
                double price = Double.parseDouble(parts[3]);
                items.add(new InventoryItem(itemId, name, quantity, price));
            }
        } catch (FileNotFoundException e) {
            try {
                new File(FILE_NAME).createNewFile();
            } catch (IOException ioException) {
                throw new FileIOException("Error creating file.");
            }
        } catch (IOException e) {
            throw new FileIOException("Error reading file.");
        }
        return items;
    }

    public void writeInventoryItems(List<InventoryItem> items) throws FileIOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (InventoryItem item : items) {
                writer.write(item.getItemId() + "," + item.getName() + "," + item.getQuantity() + "," + item.getPrice());
                writer.newLine();
            }
        } catch (FileNotFoundException e) {
            try {
                new File(FILE_NAME).createNewFile();
            } catch (IOException ioException) {
                throw new FileIOException("Error creating file.");
            }
        } catch (IOException e) {
            throw new FileIOException("Error writing to file.");
        }
    }

    public void addItem(InventoryItem newItem) throws FileIOException, InvalidDataException {
        List<InventoryItem> items = readInventoryItems();

        for (InventoryItem item : items) {
            if (item.getItemId() == newItem.getItemId()) {
                throw new InvalidDataException("Item already exists.");
            }
        }

        items.add(newItem);

        writeInventoryItems(items);
    }

    public InventoryItem findItem(int itemId) throws FileIOException, ItemNotFoundException {
        List<InventoryItem> items = readInventoryItems();

        for (InventoryItem item : items) {
            if (item.getItemId() == itemId) {
                return item;
            }
        }

        throw new ItemNotFoundException("Item not found.");
    }

    public void updateItem(InventoryItem newItem) throws FileIOException, ItemNotFoundException {
        List<InventoryItem> items = readInventoryItems();

        int index = -1;
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).getItemId() == newItem.getItemId()) {
                index = i;
                break;
            }
        }

        if (index == -1) {
            throw new ItemNotFoundException("Item not found.");
        }

        items.set(index, newItem);

        writeInventoryItems(items);
    }

    public void deleteItem(int itemId) throws ItemNotFoundException, FileIOException {
        List<InventoryItem> items = readInventoryItems();

        int index = -1;
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).getItemId() == itemId) {
                index = i;
                break;
            }
        }

        if (index == -1) {
            throw new ItemNotFoundException("Item not found.");
        }

        items.remove(index);

        writeInventoryItems(items);
    }
}
