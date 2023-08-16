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
        HashMap<String, Integer> countOfItems = new HashMap<>();
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
    public List<ReceiptItem> calculateItemsCost(List<ReceiptItem> receiptItems) {


        receiptItems.forEach(receiptItem -> {
            receiptItem.setSubTotal(receiptItem.getQuantity(), receiptItem.getUnitPrice());
        });

        return receiptItems;
    }

    public int calculateTotalPrice(List<ReceiptItem> receiptItems) {

        return receiptItems.stream().mapToInt(ReceiptItem::getSubTotal).sum();
    }
    public Receipt calculateCost(List<ReceiptItem> receiptItems) {
        List<ReceiptItem> receiptItemsWithSubtotal = calculateItemsCost(receiptItems);
        Receipt receipt = new Receipt();
        receipt.setReceiptItems(receiptItemsWithSubtotal);
        receipt.setTotalPrice(calculateTotalPrice(receiptItemsWithSubtotal));
        return receipt;
    }
    public String generateItemsReceipt(Receipt receipt) {
        StringBuilder itemsReceipt = new StringBuilder();
        receipt.getReceiptItems().forEach(receiptItem -> itemsReceipt.append("Name: ").append(receiptItem.getName())
                .append(", Quantity: ").append(receiptItem.getQuantity())
                .append(", Unit price: ").append(receiptItem.getUnitPrice()).append(" (yuan)")
                .append(", Subtotal: ").append(receiptItem.getSubTotal()).append(" (yuan)").append("\n"));
        return itemsReceipt.toString();
    }
    public String generateReceipt(String itemsReceipt, int totalPrice) {
        return "***<store earning no money>Receipt***" + "\n" +
                itemsReceipt +
                "----------------------" + "\n" +
                "Total: " + totalPrice + " (yuan)" + "\n" +
                "**********************";
    }
    public String renderReceipt(Receipt receipt) {
        String itemsReceipt = generateItemsReceipt(receipt);
        return generateReceipt(itemsReceipt, receipt.getTotalPrice());
    }


}
