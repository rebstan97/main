package seedu.address.ui.sales;

import java.util.Map;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import seedu.address.model.ingredient.IngredientName;
import seedu.address.model.salesrecord.SalesRecord;
import seedu.address.ui.UiPart;

/**
 * Stack panel to display each sales record's details
 */
public class RecordStackPanel extends UiPart<Region> {
    private static final String FXML = "RecordStackPanel.fxml";

    public final SalesRecord salesRecord;

    @FXML
    private StackPane stackPane;
    @FXML
    private Label date;
    @FXML
    private Label itemName;
    @FXML
    private Label quantitySold;
    @FXML
    private Label price;
    @FXML
    private Label totalRevenue;
    @FXML
    private Label ingredientUsed;

    public RecordStackPanel(SalesRecord salesRecord) {
        super(FXML);
        this.salesRecord = salesRecord;
        date.setText(salesRecord.getDate().toString());
        itemName.setText(salesRecord.getName().toString());
        quantitySold.setText(salesRecord.getQuantitySold().toString());
        price.setText(salesRecord.getPrice().toString());
        totalRevenue.setText(String.valueOf(salesRecord.getRevenue()));
        ingredientUsed.setText(ingredientUsedToString(salesRecord.getIngredientsUsed()));
    }

    /**
     * Returns the string representation of the given {@code ingredientUsed}
     */
    private String ingredientUsedToString(Map<IngredientName, Integer> ingredientUsed) {
        StringBuilder stringBuilder = new StringBuilder();
        int index = 1;
        for (Map.Entry<IngredientName, Integer> entry : ingredientUsed.entrySet()) {
            stringBuilder.append(index).append(". ").append(entry.getKey().toString())
                    .append("\t\t\t").append(entry.getValue().toString());
            index ++;
        }
        return stringBuilder.toString();
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }
        // instanceof handles nulls
        if (!(other instanceof RecordStackPanel)) {
            return false;
        }
        // state check
        RecordStackPanel recordStackPanel = (RecordStackPanel) other;
        return salesRecord.equals(recordStackPanel.salesRecord);
    }
}
