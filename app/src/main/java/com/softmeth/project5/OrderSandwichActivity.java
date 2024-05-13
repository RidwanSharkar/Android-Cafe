package com.softmeth.project5;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.List;

/**
 * Activity for ordering sandwiches within the RU Café system.
 * Allows users to choose meat, bread, and various add-ons, calculates and displays the price, and
 * lets them add their configured sandwich to their order.
 *
 * @author Ridwan Sharkar
 */
public class OrderSandwichActivity extends AppCompatActivity
{
    private RadioGroup breadRadioGroup;
    private RadioGroup meatRadioGroup;
    private CheckBox cheeseCheckBox, lettuceCheckBox, tomatoesCheckBox, onionsCheckBox;
    private TextView subTotalTextView;
    private Button addToOrderButton;
    /*============================================================================================*/

    /**
     * initializes the UI components and sets up listeners
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_sandwich);
        initializeViews();
        setupRadioGroupListeners();
        setupCheckBoxListeners();
        addToOrderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAddToOrderConfirmationDialog();
            }
        });
    }

    /**
     * Initializes all view components used in the activity.
     */
    private void initializeViews()
    {
        breadRadioGroup = findViewById(R.id.breadChoiceRadioGroup);
        meatRadioGroup = findViewById(R.id.meatChoiceRadioGroup);
        cheeseCheckBox = findViewById(R.id.CheeseCheckBox);
        lettuceCheckBox = findViewById(R.id.LettuceCheckBox);
        tomatoesCheckBox = findViewById(R.id.TomatoesCheckBox);
        onionsCheckBox = findViewById(R.id.OnionsCheckBox);
        subTotalTextView = findViewById(R.id.SubTotalSandwichTextView);
        addToOrderButton = findViewById(R.id.AddToOrderSandwichButton);
    }

    /**
     * Displays a confirmation dialog to the user to confirm the addition of the sandwich to their order
     */
    private void showAddToOrderConfirmationDialog() {
        RadioButton selectedMeat = findViewById(meatRadioGroup.getCheckedRadioButtonId());
        RadioButton selectedBread = findViewById(breadRadioGroup.getCheckedRadioButtonId());
        if (selectedMeat == null || selectedBread == null) {
            Toast.makeText(this, "Please select both a meat and a bread option.", Toast.LENGTH_SHORT).show();
            return;
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Confirm Order");
        builder.setMessage("Are you sure you want to add this sandwich to your order?");

        builder.setPositiveButton("Yes", (dialog, which) -> {
            addSandwichToOrder();
        });

        builder.setNegativeButton("No", (dialog, which) -> dialog.dismiss());

        AlertDialog dialog = builder.create();
        dialog.show();



    }

    /**
     * Sets up listeners for the radio groups that handle meat and bread choices.
     */
    private void setupRadioGroupListeners()
    {
        meatRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                updateSubtotal();
            }
        });
        breadRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                updateSubtotal();
            }
        });
    }

    /**
     * Sets up listeners for addons checkboxes
     */
    private void setupCheckBoxListeners()
    {
        cheeseCheckBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            updateSubtotal();
        });
        lettuceCheckBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            updateSubtotal();
        });
        tomatoesCheckBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            updateSubtotal();
        });
        onionsCheckBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            updateSubtotal();
        });
    }

    /**
     * Updates the subtotal displayed based on the current selections of meat, bread, and any add-ons.
     */
    private void updateSubtotal() {
        RadioButton selectedMeat = findViewById(meatRadioGroup.getCheckedRadioButtonId());
        RadioButton selectedBread = findViewById(breadRadioGroup.getCheckedRadioButtonId());
        if (selectedMeat == null || selectedBread == null) {
            return;
        }
        String meatChoice = selectedMeat.getText().toString().toUpperCase();
        String breadChoice = selectedBread.getText().toString();
        List<String> addOns = getSelectedAddOns();
        try {
            Sandwich sandwich = Sandwich.createSandwich(meatChoice, breadChoice, addOns);
            double subtotal = sandwich.price();
            subTotalTextView.setText(String.format("$%.2f", subtotal));
        } catch (IllegalArgumentException e) {
            Toast.makeText(this, "Invalid selection: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    /**
     * Retrieves a list of selected add-ons based on the state of the checkboxes.
     */
    private List<String> getSelectedAddOns() {
        List<String> addOns = new ArrayList<>();
        if (cheeseCheckBox.isChecked()) addOns.add("CHEESE");
        if (lettuceCheckBox.isChecked()) addOns.add("LETTUCE");
        if (tomatoesCheckBox.isChecked()) addOns.add("TOMATOES");
        if (onionsCheckBox.isChecked()) addOns.add("ONIONS");
        return addOns;
    }

    /**
     * Adds the configured sandwich to the current order after validating the selections.
     */
    private void addSandwichToOrder() {
        RadioButton selectedMeat = findViewById(meatRadioGroup.getCheckedRadioButtonId());
        RadioButton selectedBread = findViewById(breadRadioGroup.getCheckedRadioButtonId());
        List<String> addOns = getSelectedAddOns();

        Sandwich sandwich = Sandwich.createSandwich(selectedMeat.getText().toString().toUpperCase(),
                selectedBread.getText().toString(), addOns);
        Order currentOrder = Order.getInstance();
        currentOrder.addItem(sandwich);
        Toast.makeText(this, "Sandwich added to order!", Toast.LENGTH_SHORT).show();
    }




}
