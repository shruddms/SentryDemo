package com.kyungeun.sentrydemo

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.kyungeun.sentrydemo.databinding.ActivityMainBinding
import io.sentry.Sentry
import io.sentry.SentryLevel
import io.sentry.protocol.User

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.sendErrorBtn.setOnClickListener {
            try {
                throw Exception("Error Test")
            } catch (e: Exception) {
                Sentry.captureException(Exception("my error"))
                Toast.makeText(this, "Completion!", Toast.LENGTH_SHORT).show()
            }
        }
        binding.sendErrorTagBtn.setOnClickListener {
            try {
                throw Exception("Error Test")
            } catch (e: Exception) {
                Sentry.captureException(Exception("my error")) { scope ->
                    scope.setTag("my-tag", "my value")
                    scope.level = SentryLevel.WARNING
                }
                Toast.makeText(this, "Completion!", Toast.LENGTH_SHORT).show()
            }
        }
        binding.messageBtn.setOnClickListener {
            Sentry.captureMessage("Message Test")
            Toast.makeText(this, "Completion!", Toast.LENGTH_SHORT).show()
        }
        binding.contextBtn.setOnClickListener {
            Sentry.configureScope { scope ->
                scope.setContexts("name", "demo")
                scope.setContexts("age", 20)
                scope.setContexts("gender", "female")
                scope.setContexts("member_rating", 1)
                scope.setContexts("purchase_rating", 0)
            }
        }
        binding.addUserBtn.setOnClickListener {
            Sentry.configureScope { scope ->
                scope.setTag("user", "login")
                scope.level = SentryLevel.DEBUG
                scope.user = User().apply {
                    id = "123456789"
                    username = "demo"
                    email = "demo@example.com"
                }
            }
            Toast.makeText(this, "Completion!", Toast.LENGTH_SHORT).show()
        }
        binding.clearUserBtn.setOnClickListener {
            Sentry.configureScope { scope ->
                scope.setTag("user", "logout")
                scope.user = null
            }
            Toast.makeText(this, "Completion!", Toast.LENGTH_SHORT).show()
        }
    }
}