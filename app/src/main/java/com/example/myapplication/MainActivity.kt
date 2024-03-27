package com.example.myapplication

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val loginButton = findViewById<Button>(R.id.login_button)
        val phoneField = findViewById<EditText>(R.id.phone_field)

        loginButton.setOnClickListener {
            val phone = phoneField.text.toString()
            if (validatePhone(phone)) {
                // Inicie o processo de login com o Firebase Auth
            } else {
                // Mostre erro
            }
        }
    }

    private fun validatePhone(phone: String): Boolean {
        // Valide o número de telefone
        return phone.matches(Regex("...")) // Substitua "..." pelo padrão adequado
    }
}

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthProvider
import java.util.concurrent.TimeUnit

// Defina a variável para o FirebaseAuth
private lateinit var auth: FirebaseAuth

override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_login)

    // Inicialize a instância do FirebaseAuth
    auth = FirebaseAuth.getInstance()

    // Outro código...

    loginButton.setOnClickListener {
        val phone = phoneField.text.toString()
        if (validatePhone(phone)) {
            startPhoneNumberVerification(phone)
        } else {
            // Mostre erro
        }
    }
}

private fun startPhoneNumberVerification(phoneNumber: String) {
    val options = PhoneAuthProvider.verifyPhoneNumberOptionsBuilder()
            .setPhoneNumber(phoneNumber)
            .setTimeout(60L, TimeUnit.SECONDS)
            .setActivity(this)
            .setCallbacks(callbacks)
            .build()
    PhoneAuthProvider.verifyPhoneNumber(options)
}

// Crie os callbacks de verificação
private val callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
    override fun onVerificationCompleted(credential: PhoneAuthCredential) {
        // Autenticação foi completada automaticamente
    }

    override fun onVerificationFailed(e: FirebaseException) {
        // A verificação falhou
    }

    override fun onCodeSent(verificationId: String, token: PhoneAuthProvider.ForceResendingToken) {
        // O código foi enviado; agora você precisa pedir ao usuário para inserir o código e então verificar
    }
}
