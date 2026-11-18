package com.trg.pbbvoting

import android.app.Activity
import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.GradientDrawable
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Patterns
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.view.WindowInsets
import android.widget.Button
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.ScrollView
import android.widget.TextView
import android.widget.Toast

class MainActivity : Activity() {
    private val navy = Color.rgb(12, 32, 66)
    private val teal = Color.rgb(0, 126, 112)
    private val gold = Color.rgb(190, 139, 25)

    private fun dp(value: Int): Int =
        (value * resources.displayMetrics.density + 0.5f).toInt()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val root = FrameLayout(this)

        val photo = ImageView(this).apply {
            setImageResource(R.drawable.voting_background)
            scaleType = ImageView.ScaleType.CENTER_CROP
            contentDescription = null
        }
        root.addView(
            photo,
            FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
        )

        // A navy veil keeps the supplied photo visible while separating it from the form.
        root.addView(
            View(this).apply { setBackgroundColor(Color.argb(106, 7, 20, 42)) },
            FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
        )

        val scroll = ScrollView(this).apply {
            isFillViewport = false
            clipToPadding = false
            setPadding(dp(18), dp(26), dp(18), dp(26))
            overScrollMode = View.OVER_SCROLL_IF_CONTENT_SCROLLS
        }

        val panel = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            gravity = Gravity.CENTER_HORIZONTAL
            setPadding(dp(22), dp(22), dp(22), dp(24))
            background = GradientDrawable().apply {
                setColor(Color.argb(248, 255, 255, 255))
                cornerRadius = dp(24).toFloat()
                setStroke(dp(1), Color.argb(70, 12, 32, 66))
            }
            elevation = dp(8).toFloat()
        }
        scroll.addView(
            panel,
            FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
        )
        root.addView(
            scroll,
            FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
        )

        val logo = ImageView(this).apply {
            setImageResource(R.mipmap.ic_launcher)
            scaleType = ImageView.ScaleType.FIT_CENTER
            contentDescription = "T.R.G. P.B.B. Voting logo"
        }
        panel.addView(
            logo,
            LinearLayout.LayoutParams(dp(88), dp(88)).apply {
                gravity = Gravity.CENTER_HORIZONTAL
                bottomMargin = dp(8)
            }
        )

        val title = TextView(this).apply {
            text = "T.R.G"
            textSize = 27f
            setTypeface(Typeface.DEFAULT, Typeface.BOLD)
            setTextColor(navy)
            gravity = Gravity.CENTER
        }
        panel.addView(title)

        val subtitle = TextView(this).apply {
            text = "P.B.B. VOTING"
            textSize = 22f
            setTypeface(Typeface.DEFAULT, Typeface.BOLD)
            setTextColor(teal)
            gravity = Gravity.CENTER
        }
        panel.addView(subtitle)

        val tagline = TextView(this).apply {
            text = "Choose your contestant"
            textSize = 15f
            setTextColor(Color.rgb(91, 101, 113))
            gravity = Gravity.CENTER
            setPadding(0, dp(4), 0, dp(14))
        }
        panel.addView(tagline)

        val name = EditText(this).apply {
            hint = "Enter voter's name"
            inputType = android.text.InputType.TYPE_CLASS_TEXT or
                android.text.InputType.TYPE_TEXT_FLAG_CAP_WORDS
            textSize = 17f
            setTextColor(navy)
            setHintTextColor(Color.rgb(105, 112, 121))
            backgroundTintList = ColorStateList.valueOf(teal)
            minHeight = dp(54)
        }
        panel.addView(name, fieldParams())

        val email = EditText(this).apply {
            hint = "Organizer's email address"
            inputType = android.text.InputType.TYPE_CLASS_TEXT or
                android.text.InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS
            textSize = 17f
            setTextColor(navy)
            setHintTextColor(Color.rgb(105, 112, 121))
            backgroundTintList = ColorStateList.valueOf(teal)
            minHeight = dp(54)
        }
        panel.addView(email, fieldParams())

        val label = TextView(this).apply {
            text = "Choose your contestant:"
            textSize = 18f
            setTypeface(Typeface.DEFAULT, Typeface.BOLD)
            setTextColor(navy)
            setPadding(0, dp(12), 0, dp(4))
        }
        panel.addView(label)

        val group = RadioGroup(this).apply {
            orientation = RadioGroup.VERTICAL
        }
        val contestants = listOf("Yumi", "Pula", "Kenshin Mark", "Macky")
        contestants.forEach { contestant ->
            val radio = RadioButton(this).apply {
                text = contestant
                textSize = 18f
                setTextColor(navy)
                buttonTintList = ColorStateList.valueOf(teal)
                minHeight = dp(48)
                id = View.generateViewId()
            }
            group.addView(
                radio,
                RadioGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    dp(48)
                )
            )
        }
        panel.addView(group)

        val submit = Button(this).apply {
            text = "SUBMIT VOTE"
            textSize = 16f
            setTypeface(Typeface.DEFAULT, Typeface.BOLD)
            setTextColor(Color.WHITE)
            backgroundTintList = ColorStateList.valueOf(teal)
            minHeight = dp(54)
        }
        panel.addView(
            submit,
            LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                dp(54)
            ).apply {
                topMargin = dp(12)
            }
        )

        submit.setOnClickListener {
            val voter = name.text.toString().trim()
            val recipient = email.text.toString().trim()
            val selectedId = group.checkedRadioButtonId

            if (voter.isEmpty() ||
                !Patterns.EMAIL_ADDRESS.matcher(recipient).matches() ||
                selectedId == -1
            ) {
                Toast.makeText(
                    this,
                    "Complete all fields and select a contestant",
                    Toast.LENGTH_LONG
                ).show()
                return@setOnClickListener
            }

            val selected = findViewById<RadioButton>(selectedId).text.toString()
            val intent = Intent(Intent.ACTION_SENDTO).apply {
                data = Uri.parse("mailto:")
                putExtra(Intent.EXTRA_EMAIL, arrayOf(recipient))
                putExtra(Intent.EXTRA_SUBJECT, "PBB TRG Edition Vote")
                putExtra(
                    Intent.EXTRA_TEXT,
                    "Voter: $voter\nContestant: $selected"
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

        // Android 15+ draws edge-to-edge; keep the form clear of system bars.
        if (Build.VERSION.SDK_INT >= 35) {
            root.setOnApplyWindowInsetsListener { _, insets ->
                val bars = insets.getInsets(WindowInsets.Type.systemBars())
                scroll.setPadding(
                    dp(18) + bars.left,
                    dp(26) + bars.top,
                    dp(18) + bars.right,
                    dp(26) + bars.bottom
                )
                insets
            }
        }

        setContentView(root)
    }

    private fun fieldParams() = LinearLayout.LayoutParams(
        ViewGroup.LayoutParams.MATCH_PARENT,
        ViewGroup.LayoutParams.WRAP_CONTENT
    ).apply {
        bottomMargin = dp(4)
    }
}
