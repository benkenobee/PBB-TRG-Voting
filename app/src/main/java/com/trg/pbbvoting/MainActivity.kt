
package com.trg.pbbvoting

import android.app.Activity
import android.os.Bundle
import android.content.Intent
import android.net.Uri
import android.util.Patterns
import android.widget.*
import android.view.ViewGroup

class MainActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val layout = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(40, 50, 40, 30)
        }

        val scroll = ScrollView(this)
        scroll.addView(layout)

        val title = TextView(this).apply {
            text = "P.B.B T.RG EDITION\nVOTING"
            textSize = 26f
            gravity = 17
        }
        layout.addView(title)

        val name = EditText(this).apply {
            hint = "Enter voter's name"
            inputType =  android.text.InputType.TYPE_CLASS_TEXT
        }
        layout.addView(name)

        val email = EditText(this).apply {
            hint = "Organizer's email address"
            inputType = 33
        }
        layout.addView(email)

        val label = TextView(this).apply {
            text = "Choose your contestant:"
            textSize = 20f
        }
        layout.addView(label)

        val group = RadioGroup(this)
        val contestants = listOf(
            "Yumi", "Pula",
            "Kenshin Mark", "Macky"
        )

        contestants.forEach { contestant ->
            val radio = RadioButton(this).apply {
                text = contestant
                textSize = 19f
            }
            group.addView(radio)
        }
        layout.addView(group)

        val submit = Button(this).apply {
            text = "SUBMIT VOTE"
        }
        layout.addView(submit)

        submit.setOnClickListener {
            val voter = name.text.toString().trim()
            val recipient = email.text.toString().trim()
            val selectedId = group.checkedRadioButtonId

            if (voter.isEmpty() ||
                !Patterns.EMAIL_ADDRESS.matcher(recipient).matches() ||
                selectedId == -1) {
                Toast.makeText(
                    this,
                    "Complete all fields and select a contestant",
                    Toast.LENGTH_LONG
                ).show()
                return@setOnClickListener
            }

            val selected = findViewById<RadioButton>(
                selectedId
            ).text.toString()

            val intent = Intent(Intent.ACTION_SENDTO).apply {
                data = Uri.parse("mailto:")
                putExtra(
                    Intent.EXTRA_EMAIL,
                    arrayOf(recipient)
                )
                putExtra(
                    Intent.EXTRA_SUBJECT,
                    "PBB TRG Edition Vote"
                )
                putExtra(
                    Intent.EXTRA_TEXT,
                    "Voter: $voter\n" +
                    "Contestant: $selected"
                )
            }

            try {
                startActivity(intent)
            } catch (e: Exception) {
                Toast.makeText(
                    this,
                    "Please install an email app",
                    Toast.LENGTH_LONG
                ).show()
            }
        }

        setContentView(scroll)
    }
}
