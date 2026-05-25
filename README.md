# AdoptMe

> **¿Quieres que te expliquen el código línea por línea?** Lee también → [`README-CODIGO.md`](README-CODIGO.md)

App de adopción de mascotas hecha en **Android Studio** con **Kotlin**.  
Proyecto de clase (tipo semana 2-3): pantallas en XML, Activities y navegación básica.

El diseño se basó en los mockups de Figma (login + página de inicio).

---

## Qué hace la app

- **Inicio:** página simple con texto, números, pasos y un botón para registrarse (sin diseño tipo web gigante).
- **Login:** correo y contraseña (con validación simple).
- **Registro:** crear cuenta (simulado, no hay servidor real).
- **Recuperar contraseña:** pides el correo y sale un mensaje (también simulado).
- **Barra de abajo:** Inicio, Sesión, Registro.
- Si inicias sesión, el app recuerda el correo en el celular (`SharedPreferences`).

> Todavía **no** hay base de datos ni Firebase. El login es local para practicar.

---

## Cómo correrlo

1. Abre la carpeta `AdoptMe` en Android Studio.
2. Espera el **Gradle Sync**.
3. Enciende un **emulador** o conecta el celular (depuración USB).
4. Dale **Run** ▶.

La app abre en **Inicio**.

### Si sale "Device is offline"

El emulador o el celu se desconectó. Cierra y abre el emulador otra vez, o en terminal:

```
adb kill-server
adb start-server
```

---

## Pantallas

| Pantalla | Archivo Kotlin | Layout |
|----------|----------------|--------|
| Inicio | `MainActivity.kt` | `activity_main.xml` |
| Login | `LoginActivity.kt` | `activity_login.xml` |
| Registro | `RegisterActivity.kt` | `activity_register.xml` |
| Recuperar clave | `ForgotPasswordActivity.kt` | `activity_forgot_password.xml` |

---

## Cómo se navega

- Barra de abajo → **Sesión** = login, **Registro** = crear cuenta.
- En login: **Regístrate**, **¿Olvidaste tu contraseña?**, botón naranja para entrar.
- Flecha arriba a la izquierda = volver atrás.
- Menú ☰ en inicio = mismas opciones + cerrar sesión si ya entraste.

---

## Carpetas importantes

```
app/src/main/
├── java/com/example/adoptme/    ← código Kotlin
├── res/layout/                  ← pantallas XML
├── res/drawable/                ← iconos y fondos
├── res/values/                  ← colores, textos, tema
└── AndroidManifest.xml          ← qué Activity abre primero (MainActivity)
```

### Archivos Kotlin (para qué sirven)

- `MainActivity` — pantalla principal
- `LoginActivity` — login
- `RegisterActivity` — registro
- `ForgotPasswordActivity` — recuperar contraseña
- `Navigation.kt` — abre las otras pantallas con `Intent`
- `SessionManager.kt` — guarda si ya iniciaste sesión
- `Validation.kt` — revisa que el correo y la clave estén bien
- `ToolbarHelper.kt` — pone la barra de arriba con botón atrás

---

## Lo que fuimos haciendo (orden)

1. Proyecto vacío en Android Studio.
2. Colores y textos en español (`colors.xml`, `strings.xml`).
3. Pantalla de login según el mockup.
4. Pantalla de inicio con scroll (hero, stats, pasos, footer).
5. Navegación entre pantallas + barra inferior.
6. Validación de campos y guardar sesión en el celular.

---

## Validaciones (simples)

- Correo: que tenga `@` y `.`
- Contraseña: mínimo 4 caracteres
- En registro: nombre obligatorio y que las dos contraseñas sean iguales

---

## Colores que usamos

- Fondo crema/beige
- Marrón para textos
- Naranja para botones

Están en `res/values/colors.xml`.

---

## Cosas que se pueden mejorar después

- Poner fotos reales de mascotas (ahora hay cuadros de color de placeholder).
- Conectar Firebase o un backend de verdad.
- Lista de mascotas para adoptar.

