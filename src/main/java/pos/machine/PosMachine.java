package pos.machine;

import java.util.*;
import java.util.stream.Collectors;

public class PosMachine {
    public String printReceipt(List<String> barcodes) {

        List<ReceiptItem> receiptItems =decodeToItems(barcodes);
        Receipt receipt = calculateCost(receiptItems);
        return renderReceipt(receipt);

    }
    public List<ReceiptItem> decodeToItems(List<String> barcodes){
        List<Item> items = ItemsLoader.loadAllItems();
        List<ReceiptItem> receiptItems =new ArrayList<>();
        List<String> specificBarcodes = barcodes.stream().distinct().collect(Collectors.toList());
        //count of barcodes
        HashMap<String, Integer> countOfItems = new HashMap<String, Integer>();
        specificBarcodes.forEach(barcode -> countOfItems.put(barcode, Collections.frequency(barcodes,barcode)));

        items.forEach(item -> {
            ReceiptItem receiptItem = new ReceiptItem();
            receiptItem.setName(item.getName());
            receiptItem.setUnitPrice(item.getPrice());
            receiptItem.setQuantity(countOfItems.get(item.getBarcode()));
            receiptItems.add(receiptItem);
        });
        return receiptItems;
    }
    public List<ReceiptItem> calculatePerItem(List<ReceiptItem> receiptItems) {
        receiptItems.forEach(receiptItem -> receiptItem.setSubTotal(receiptItem.getQuantity() * receiptItem.getUnitPrice()));
        return receiptItems;
    }

    public int calculateTotalPrice(List<ReceiptItem> receiptItems) {
        return receiptItems.stream().mapToInt(ReceiptItem::getSubTotal).sum();
    }
}
