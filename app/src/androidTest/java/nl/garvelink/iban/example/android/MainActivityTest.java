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

import android.support.test.espresso.matcher.ViewMatchers;
import android.test.ActivityInstrumentationTestCase2;
import android.test.suitebuilder.annotation.Smoke;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.pressImeActionButton;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.containsString;

@Smoke
public class MainActivityTest extends ActivityInstrumentationTestCase2<MainActivity> {

    public MainActivityTest() {
        super(MainActivity.class);
    }

    public void setUp() throws Exception {
        super.setUp();
        getActivity();
    }

    public void testValidIBAN() {
        onView(ViewMatchers.withId(R.id.main__iban_value))
                .perform(typeText("AL47212110090000000235698741"))
                .perform(pressImeActionButton());

        onView(withId(R.id.main__result)).check(matches(withText(
                allOf(
                        containsString("Valid        : YES"),
                        containsString("Pretty print : AL47 2121 1009 0000 0002 3569 8741"),
                        containsString("Xfer format  : AL47212110090000000235698741"),
                        containsString("Country code : AL"),
                        containsString("Check digits : 47"),
                        containsString("Bank ID      : 212"),
                        containsString("Branch ID    : 1100")
                ))));
    }

    public void testInvalidIBAN() {
        onView(withId(R.id.main__iban_value))
                .perform(typeText("ZZ44ABCD1234"));

        onView(withId(R.id.main__submit))
                .perform(click());

        onView(withId(R.id.main__result)).check(matches(withText(
                containsString("Valid        : NO")
        )));
    }
}
