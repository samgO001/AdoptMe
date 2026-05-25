# AdoptMe — Guía línea por línea del código

Este es el **segundo README**. El primero (`README.md`) explica el proyecto en general; **este archivo** explica **cada parte del código** y para qué sirve.

> Tip: en Android Studio puedes abrir un `.kt` o `.xml` al lado de este archivo y seguir los números de línea.

---

## Índice

1. [Conceptos que debes conocer antes](#1-conceptos-que-debes-conocer-antes)
2. [MainActivity.kt](#2-mainactivitykt)
3. [LoginActivity.kt](#3-loginactivitykt)
4. [RegisterActivity.kt](#4-registeractivitykt)
5. [ForgotPasswordActivity.kt](#5-forgotpasswordactivitykt)
6. [Navigation.kt](#6-navigationkt)
7. [SessionManager.kt](#7-sessionmanagerkt)
8. [Validation.kt](#8-validationkt)
9. [ToolbarHelper.kt](#9-toolbarhelperkt)
10. [activity_main.xml](#10-activity_mainxml)
11. [activity_login.xml](#11-activity_loginxml)
12. [activity_register.xml y activity_forgot_password.xml](#12-activity_registerxml-y-activity_forgot_passwordxml)
13. [AndroidManifest.xml](#13-androidmanifestxml)
14. [Menús, colores y drawables](#14-menús-colores-y-drawables)
15. [Cómo se conecta todo](#15-cómo-se-conecta-todo)

---

## 1. Conceptos que debes conocer antes

| Concepto | Qué es en simple |
|----------|------------------|
| **Activity** | Una pantalla de la app (como un Activity = una ventana). |
| **Layout (XML)** | El diseño visual de la pantalla (botones, textos, colores). |
| **findViewById** | Busca un elemento del XML por su `android:id` para usarlo en Kotlin. |
| **Intent** | Mensaje para abrir otra Activity (cambiar de pantalla). |
| **onCreate** | Método que se ejecuta cuando la pantalla se crea (ahí va casi todo el código). |
| **setOnClickListener** | Qué hacer cuando el usuario toca un botón o texto. |
| **Toast** | Mensajito pequeño abajo (ej: "Sesión iniciada"). |
| **SharedPreferences** | Guardar datos chiquitos en el celular (como si ya hizo login). |
| **R.layout / R.id / R.string** | Archivos generados automáticamente que conectan Kotlin con XML y textos. |

---

## 2. MainActivity.kt

**Archivo:** `app/src/main/java/com/example/adoptme/MainActivity.kt`  
**Pantalla:** Inicio (la que abre al prender la app).

| Línea | Código | Para qué sirve |
|-------|--------|----------------|
| 1 | `package com.example.adoptme` | Nombre del paquete de la app (carpeta del código). |
| 3-9 | `import ...` | Trae clases que vamos a usar (Bundle, Button, Toast, etc.). |
| 11 | `// Pantalla principal...` | Comentario para nosotros, no afecta la app. |
| 12 | `class MainActivity : AppCompatActivity()` | Declara la pantalla principal; hereda de `AppCompatActivity` (pantalla estándar Android). |
| 14-15 | `private lateinit var bottomNav` / `scrollHome` | Variables que guardarán la barra de abajo y el scroll; `lateinit` = las llenamos en `onCreate`, no al crear la clase. |
| 17 | `override fun onCreate(...)` | Android llama esto solo al abrir la pantalla. |
| 18 | `super.onCreate(savedInstanceState)` | Obligatorio: deja que la clase padre haga su parte (guardar estado si rotas el celular, etc.). |
| 19 | `setContentView(R.layout.activity_main)` | Carga el diseño XML `activity_main.xml` en esta pantalla. |
| 21-22 | `findViewById(R.id.scrollHome)` | Conecta la variable Kotlin con el `ScrollView` que tiene `id=scrollHome` en el XML. |
| 24-40 | `bottomNav.setOnItemSelectedListener` | Cuando tocan un ítem de la barra de abajo: |
| 26-28 | `nav_home` → `smoothScrollTo(0,0)` + `true` | "Inicio" sube el scroll al tope; `true` = deja ese ítem seleccionado. |
| 30-32 | `nav_login` → `Navigation.openLogin` + `false` | Abre login; `false` = no deja marcado "Sesión" en la barra (porque no estamos en esa pantalla). |
| 34-36 | `nav_register` → `Navigation.openRegister` | Abre registro. |
| 38 | `else -> false` | Cualquier otro id no hace nada. |
| 42-44 | `imgLogo.setOnClickListener` | Tocar el logo también sube el scroll (como volver arriba). |
| 46-48 | `btnRegister` → `openRegister` | Botón naranja "Regístrate" del contenido. |
| 49-51 | `btnGoLogin` → `openLogin` | Botón "Inicio de Sesión". |
| 53-54 | `SessionManager.getEmail` / `isLoggedIn` | Pregunta si ya había sesión guardada. |
| 55-59 | `Toast.makeText(... welcome_user ...)` | Si sí hay sesión, muestra "Bienvenido, nombre" (nombre = parte antes del @ del correo). |
| 63-66 | `onResume` + `bottomNav.selectedItemId = nav_home` | Cada vez que vuelves a esta pantalla, la barra marca "Inicio" otra vez. |

---

## 3. LoginActivity.kt

**Archivo:** `LoginActivity.kt`  
**Pantalla:** Iniciar sesión.

| Línea | Código | Para qué sirve |
|-------|--------|----------------|
| 11 | `class LoginActivity : AppCompatActivity()` | Pantalla de login. |
| 15 | `setContentView(R.layout.activity_login)` | Carga el XML del login. |
| 17 | `setupToolbar(...)` | Pone barra arriba con título y flecha atrás (función de `ToolbarHelper.kt`). |
| 19-20 | `findViewById` etEmail, etPassword | Referencias a los campos de texto. |
| 22 | `btnLogin.setOnClickListener` | Al tocar "Iniciar sesión": |
| 23-24 | `Validation.validateEmail/Password` | Si algo está mal, muestra error en el campo y `return` sale del listener (no sigue). |
| 26 | `etEmail.text.toString().trim()` | Lee el correo y quita espacios al inicio/final. |
| 27 | `SessionManager.login(this, email)` | Guarda en el celular que ya entró y guarda el correo. |
| 28 | `Toast ... login_success` | Mensaje de éxito. |
| 29 | `Navigation.openHome(this)` | Abre la pantalla de inicio. |
| 30 | `finish()` | Cierra el login para que al pulsar atrás no vuelvas al login si ya entraste. |
| 34-38 | `btnGoogle` | Simula login con Google (correo fijo `usuario@gmail.com`), mismo flujo. |
| 41-43 | `tvRegister` | Texto "Regístrate" → abre registro. |
| 45-47 | `tvForgotPassword` | Abre recuperar contraseña. |
| 49-51 | `tvBrand` | Tocar "AdoptMe Miuni" → vuelve al inicio. |

---

## 4. RegisterActivity.kt

**Archivo:** `RegisterActivity.kt`  
**Pantalla:** Crear cuenta.

| Línea | Código | Para qué sirve |
|-------|--------|----------------|
| 17 | `setupToolbar` | Barra con título "Regístrate" y flecha atrás. |
| 19-22 | `findViewById` nombre, email, password, confirm | Los 4 campos del formulario. |
| 24 | `btnRegister.setOnClickListener` | Al crear cuenta: |
| 25-27 | `Validation.validateName/Email/Password` | Valida los 3 primeros campos. |
| 29 | `if (!ok) return` | Si falló validación, no sigue. |
| 31-34 | Compara password vs confirm | Si no son iguales, error en el campo confirmar y sale. |
| 36 | `Toast register_success` | "Cuenta creada...". |
| 37-38 | `openLogin` + `finish` | Manda al login y cierra registro. |
| 41-44 | `tvGoLogin` | Link "Inicia sesión" → login y cierra esta pantalla. |

> **Nota:** Aquí NO llamamos `SessionManager.login` al registrar; solo simulamos crear cuenta y mandamos a login. Eso es normal en un prototipo de clase.

---

## 5. ForgotPasswordActivity.kt

**Archivo:** `ForgotPasswordActivity.kt`  
**Pantalla:** Recuperar contraseña.

| Línea | Código | Para qué sirve |
|-------|--------|----------------|
| 16 | `setupToolbar` | Título "Recuperar contraseña" + atrás. |
| 18 | `etEmail` | Campo del correo. |
| 20-23 | `btnSendLink` | Valida correo → Toast de éxito → `finish()` vuelve atrás. |
| | | No envía email real; solo practica la UI. |

---

## 6. Navigation.kt

**Archivo:** `Navigation.kt`  
**Para qué sirve el archivo entero:** Evitar escribir `Intent` en cada pantalla; un solo lugar con las rutas.

| Línea | Código | Para qué sirve |
|-------|--------|----------------|
| 8 | `object Navigation` | `object` = una sola instancia en toda la app (como utilidad estática). |
| 10 | `openHome(context, clearStack = false)` | Ir a MainActivity. |
| 11 | `Intent(context, MainActivity::class.java)` | Crea el Intent hacia MainActivity. |
| 12-16 | `if (clearStack) flags = ...` | Si `clearStack` true, borra pantallas anteriores de la pila (no lo usamos mucho aún). |
| 15 | `CLEAR_TOP \| SINGLE_TOP` | Si Main ya está abierta, la reusa en vez de apilar otra copia. |
| 17 | `startActivity(intent)` | **Abre** la pantalla. |
| 18 | `if (context is Activity && clearStack) finish()` | Si pedimos limpiar pila, cierra la pantalla actual. |
| 21-23 | `openLogin` | `startActivity` hacia LoginActivity. |
| 25-27 | `openRegister` | Hacia RegisterActivity. |
| 29-31 | `openForgotPassword` | Hacia ForgotPasswordActivity. |

---

## 7. SessionManager.kt

**Archivo:** `SessionManager.kt`  
**Para qué sirve:** Recordar si el usuario ya inició sesión (en el almacenamiento interno del celular).

| Línea | Código | Para qué sirve |
|-------|--------|----------------|
| 8-10 | `PREFS`, `KEY_LOGGED_IN`, `KEY_EMAIL` | Nombres de la "cajita" y las llaves donde guardamos datos. |
| 12-17 | `fun login` | Guarda `logged_in = true` y el `email`. |
| 13 | `getSharedPreferences(PREFS, MODE_PRIVATE)` | Abre archivo privado solo de nuestra app. |
| 14-17 | `.edit().putBoolean...putString...apply()` | Escribe y `apply()` guarda en segundo plano (no bloquea la UI). |
| 20-22 | `isLoggedIn` | Lee si `logged_in` es true (por defecto false). |
| 24-26 | `getEmail` | Devuelve el correo guardado o `null`. |
| 28-32 | `logout` | `.clear()` borra todo lo guardado de sesión. |

---

## 8. Validation.kt

**Archivo:** `Validation.kt`  
**Para qué sirve:** Revisar formularios antes de seguir.

| Línea | Código | Para qué sirve |
|-------|--------|----------------|
| 8-9 | `isValidEmail` | Regla: tiene `@`, tiene `.`, mínimo 5 caracteres. |
| 11-26 | `validateEmail(editText)` | Lee el EditText, si falla pone `editText.error = "mensaje"` y devuelve `false`. |
| 12 | `.trim()` | Quita espacios del correo. |
| 13-25 | `when { ... }` | Como varios if seguidos: vacío → error; mal formato → error; si no → limpia error y `true`. |
| 29-44 | `validatePassword` | No vacía y mínimo 4 letras (parámetro `minLength` por si quieres cambiarlo). |
| 47-55 | `validateName` | Nombre no vacío en registro. |

El `editText.error` es lo que muestra el mensaje rojo debajo del campo en Android.

---

## 9. ToolbarHelper.kt

**Archivo:** `ToolbarHelper.kt`  
**Para qué sirve:** No repetir el mismo código de toolbar en Login, Registro y Recuperar.

| Línea | Código | Para qué sirve |
|-------|--------|----------------|
| 7 | `fun AppCompatActivity.setupToolbar(...)` | **Función de extensión:** parece método de Activity pero está en otro archivo. |
| 8 | `setSupportActionBar(toolbar)` | Le dice a Android que la barra de Material es la action bar. |
| 9 | `setDisplayHomeAsUpEnabled(true)` | Muestra la flecha ←. |
| 10 | `title = title` | Pone el título que pasamos ("Iniciar sesión", etc.). |
| 11-13 | `setNavigationOnClickListener` | Al tocar la flecha, `onBackPressed()` = comportamiento de atrás. |

---

## 10. activity_main.xml

**Archivo:** `app/src/main/res/layout/activity_main.xml`  
**Diseño de:** Pantalla inicio.

| Líneas | Código | Para qué sirve |
|--------|--------|----------------|
| 1 | `<?xml version...` | Cabecera XML estándar. |
| 2-9 | `LinearLayout` raíz | Contenedor vertical que ocupa toda la pantalla; fondo crema. |
| 11-15 | `ScrollView` id=`scrollHome` | Zona que scrollea; `height=0dp` + `weight=1` = ocupa todo el espacio menos la barra de abajo. |
| 17-21 | `LinearLayout` interior | Mete todos los textos y botones en columna con padding 16dp. |
| 24-45 | Logo + nombre | `ImageView` pata + `TextView` "AdoptMe". |
| 48-63 | Título y subtítulo | Textos desde `strings.xml` (`hero_title`, `hero_subtitle`). |
| 65-71 | `ImageView` hero | Rectángulo 160dp de alto; fondo placeholder (foto después). |
| 74-80 | `home_about` | Párrafo explicando la app. |
| 83-98 | Stats | Título + texto con viñetas (+120, +90, +50). |
| 101-116 | Pasos | Título "¿Cómo funciona?" + 3 pasos en un solo TextView. |
| 119-136 | Historia | Un testimonio con fondo `bg_input`. |
| 138-147 | `btnRegister` | Botón naranja; `backgroundTint=@null` para que use nuestro drawable naranja. |
| 149-158 | `btnGoLogin` | Botón con borde. |
| 160-167 | Copyright | Texto pequeño centrado. |
| 172-180 | `BottomNavigationView` | Barra fija abajo; `menu=@menu/bottom_nav_menu` define los 3 ítems. |

**Importante:** `android:id="@+id/xxx"` crea el id que usamos en Kotlin con `R.id.xxx`.

---

## 11. activity_login.xml

**Archivo:** `activity_login.xml`

| Parte | Para qué sirve |
|-------|----------------|
| `MaterialToolbar` id=`toolbar` | Barra superior; la configura Kotlin con `setupToolbar`. |
| `ScrollView` | Por si el teclado empuja contenido en pantallas chicas. |
| `tvBrand` | "AdoptMe Miuni" clickeable en código. |
| `etEmail` / `etPassword` | Campos; `inputType` define teclado (email / contraseña oculta). |
| `tvForgotPassword` | Texto link, no es botón pero tiene click en Kotlin. |
| `btnLogin` / `btnGoogle` | Botones principales. |
| `tvRegister` | Link a registro. |

Los `@string/...` y `@drawable/...` jalan textos e imágenes de otras carpetas `res/`.

---

## 12. activity_register.xml y activity_forgot_password.xml

Misma idea que login:

- **register:** toolbar + 4 `EditText` (nombre, email, password, confirm) + botón crear + texto ir a login.
- **forgot_password:** toolbar + 1 email + botón enviar (más corto, sin scroll).

Atributos que se repiten:

| Atributo | Significado |
|----------|-------------|
| `match_parent` | Ocupa todo el ancho o alto del papá. |
| `wrap_content` | Solo el tamaño que necesita el contenido. |
| `layout_marginTop` | Espacio arriba. |
| `android:background="@drawable/bg_input"` | Fondo beige redondeado del campo. |

---

## 13. AndroidManifest.xml

**Archivo:** `AndroidManifest.xml`  
**Para qué sirve:** Le dice a Android qué pantallas existen y cuál abre primero.

| Líneas | Código | Para qué sirve |
|--------|--------|----------------|
| 5-13 | `<application ...>` | Config global: icono, nombre, tema `Theme.AdoptMe`. |
| 15-22 | `MainActivity` + `intent-filter` | **LAUNCHER** = esta abre al tocar el ícono de la app. |
| 18-20 | `MAIN` + `LAUNCHER` | Filtro obligatorio para app de inicio. |
| 24-27 | `LoginActivity` | `exported=false` = otras apps no la abren directo; `parentActivityName` = flecha atrás del sistema vuelve a Main. |
| 29-32 | `RegisterActivity` | Igual, padre Main. |
| 34-37 | `ForgotPasswordActivity` | Padre Login (atrás va al login). |

---

## 14. Menús, colores y drawables

### `bottom_nav_menu.xml`

| item | id | Abre en Kotlin |
|------|-----|----------------|
| Inicio | `nav_home` | scroll arriba |
| Sesión | `nav_login` | LoginActivity |
| Registro | `nav_register` | RegisterActivity |

### `colors.xml` (ejemplos)

| Nombre | Uso |
|--------|-----|
| `background_cream` | Fondo pantalla inicio |
| `brown_primary` | Textos marrones |
| `orange_accent` | Botón naranja |
| `input_background` | Fondo de EditText |

### Drawables (`res/drawable/`)

| Archivo | Uso |
|---------|-----|
| `bg_button_primary.xml` | Botón naranja redondeado |
| `bg_button_outline.xml` | Botón blanco con borde |
| `bg_input.xml` | Fondo campos de texto |
| `ic_paw.xml`, `ic_email.xml`, etc. | Iconos vectoriales (XML dibujado, no PNG) |

Un drawable tipo `shape` con `<corners android:radius="12dp"/>` hace esquinas redondeadas sin Photoshop.

### `strings.xml`

Todos los textos visibles están ahí para:

1. Cambiar español fácil.
2. No hardcodear strings en Kotlin/XML (`"Hola"` suelto).

En Kotlin: `getString(R.string.login_success)`  
En XML: `android:text="@string/login_success"`

### `themes.xml`

`Theme.AdoptMe` quita la action bar por defecto y pone colores naranja/marrón de la app.

---

## 15. Cómo se conecta todo

Ejemplo: usuario toca **Iniciar sesión** en login.

```
1. XML: botón btnLogin (activity_login.xml)
2. LoginActivity.kt línea 22: setOnClickListener
3. Validation.kt: revisa campos
4. SessionManager.kt: guarda sesión
5. Toast: mensaje
6. Navigation.kt openHome: Intent → MainActivity
7. finish(): cierra Login
8. MainActivity onCreate/onResume: si hay sesión, Toast bienvenida
```

Ejemplo: usuario toca **Registro** en la barra de abajo.

```
1. bottom_nav_menu.xml → id nav_register
2. MainActivity línea 34-35: Navigation.openRegister(this)
3. Navigation.kt línea 25-26: Intent RegisterActivity
4. RegisterActivity onCreate: carga activity_register.xml
```

---

## Resumen por archivo (una línea cada uno)

| Archivo | En una frase |
|---------|----------------|
| `MainActivity.kt` | Pantalla inicio, barra abajo, botones, saludo si hay sesión. |
| `LoginActivity.kt` | Login, valida, guarda sesión, va al inicio. |
| `RegisterActivity.kt` | Formulario registro, valida, manda a login. |
| `ForgotPasswordActivity.kt` | Pide correo, mensaje, atrás. |
| `Navigation.kt` | Abre pantallas con Intent. |
| `SessionManager.kt` | Guarda/lee/borra login en el celular. |
| `Validation.kt` | Errores en campos de formulario. |
| `ToolbarHelper.kt` | Flecha atrás en sub-pantallas. |
| `activity_main.xml` | Diseño inicio. |
| `activity_login.xml` | Diseño login. |
| `AndroidManifest.xml` | Lista de pantallas y cuál es la primera. |

---

## Relación con el otro README

- **`README.md`** → cómo usar el proyecto, correrlo, qué hace cada pantalla (visión general).
- **`README-CODIGO.md`** (este archivo) → qué hace **cada línea / cada bloque** de código.

Si el profe pregunta "¿qué hace este archivo?", puedes abrir la sección correspondiente aquí.

---

*Proyecto AdoptMe — documentación para estudio / entrega.*
