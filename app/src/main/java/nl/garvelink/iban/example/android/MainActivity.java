/*

    Copyright 2015 Barend Garvelink

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.

 */
package nl.garvelink.iban.example.android;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import nl.garvelink.iban.IBAN;
import nl.garvelink.iban.IBANFieldsCompat;

public class MainActivity extends AppCompatActivity
        implements TextView.OnEditorActionListener, View.OnClickListener {

    private Button submitButton;
    private EditText ibanEntry;
    private TextView resultView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ibanEntry = (EditText) findViewById(R.id.main__iban_value);
        ibanEntry.setOnEditorActionListener(this);
        submitButton = (Button) findViewById(R.id.main__submit);
        submitButton.setOnClickListener(this);
        resultView = (TextView) findViewById(R.id.main__result);
    }

    private void processIbanEntry() {
        StringBuilder result = new StringBuilder(256);
        final String rawInput = String.valueOf(ibanEntry.getText());
        result.append("Input        : ").append(rawInput).append('\n');
        result.append("--------------\n");
        try {
            IBAN iban = IBAN.parse(rawInput);
            result.append("Valid        : ").append("YES").append('\n');
            result.append("Pretty print : ").append(iban.toString()).append('\n');
            result.append("Xfer format  : ").append(iban.toPlainString()).append('\n');
            result.append("Country code : ").append(iban.getCountryCode()).append('\n');
            result.append("Check digits : ").append(iban.getCheckDigits()).append('\n');
            result.append("Bank ID      : ").append(IBANFieldsCompat.getBankIdentifier(iban)).append('\n');
            result.append("Branch ID    : ").append(IBANFieldsCompat.getBranchIdentifier(iban)).append('\n');
        } catch (IllegalArgumentException e) {
            result.append("Valid        : ").append("NO").append('\n');
            result.append("Error type   : ").append(e.getClass().getSimpleName()).append('\n');
            result.append("Error message: ").append(e.getMessage()).append('\n');
        }
        result.append('\n');
        resultView.setText(result);
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (v == ibanEntry && actionId == EditorInfo.IME_ACTION_GO) {
            processIbanEntry();
            return true;
        }
        return false;
    }

    @Override
    public void onClick(View v) {
        if (v == submitButton) {
            processIbanEntry();
        }
    }
}
