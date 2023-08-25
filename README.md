# Dispositivos Móviles
*Proyecto de la asignatura de Dispositivos Móviles.*

**Creado por:** Ariel Maldonado y Michael Cen.

Como estudiantes de la carrera de Ingeniería en Computación (Rediseño) de la Universidad Central del Ecuador, nos hemos formado para ser programadores no solo de aplicaciones web, o de escritorio, sino también de aplicaciones destinadas a dispositivos móviles. El presente proyecto contiene las funcionalidades que han sido revisadas a lo largo del semestre y que nos han permitido mejorar nuestras habilidades no solo como programadores sino también como diseñadores de una aplicación. Antes de empezar con la explicación de las funcionalidades que contiene el proyecto queremos ofrecer un agradecimiento al Ingeniero Byron Castillo por su arduo esfuerzo en la enseñanza hacia todos sus estudiantes.

## Librerías utilizadas en la aplicación

- **Picasso:**  esta es una librería que nos permite importar imágenes desde cualquier url y colocarlas en nuestros layouts.
-	**Swipe Refresh Layout:** con esta librería se implemento la funcionalidad de recargar una lista de elementos.
-	**Retrofit:** es una librería que convierte API’s en formato json o XML a interfaces de Java.
-	**Corrutinas:** librería que permite ejecutar código de forma asíncrona.
-	**Room:** esta librería nos permite manejar información en una base de datos local.
-	**Play Services Location:** con esta librería se accede a la ubicación del dispositivo siempre y cuando se cuente con los permisos.
-	**View Model:** esta librería nos permite implementar una capa mas a nuestras interfaces de usuario. Junto con view model se deben añadir las funcionalidades para manejar Live Datas.
-	**Biometric:** librería que nos permite acceder a las funciones biométricas de un dispositivo.
-	**Firebase:** Con esto podremos manejar bases de datos en la nube además de autenticación y gestión de usuarios.
-	**Lottie:** esta librería nos permite añadir gifs y animaciones hechas json a nuestra aplicación.

## Librerías implementadas por nuestra cuenta

### Lottie
Es una librería disponible para Android, iOS, aplicaciones Web y Windows que nos permite exportar animaciones almacenadas en archivos json, principalmente animaciones creadas en Adobe After Effects. Gracias a esta librería se puede ahorrar en la memoria de la aplicación el espacio que ocuparía guardar la animación como un gif, y por ello se decidió implementar esta librería.

Hay una gran cantidad de animaciones gratuitas disponibles en sitios como [LottieFiles]( https://lottiefiles.com/es/), además de una gran cantidad de usuarios que ofrecen sus animaciones. La ventaja de estas animaciones es que, como se encuentran hechas en json, estas pueden ser modificadas de alguna forma antes de descargarla, por ejemplo: podemos cambiar la paleta de colores, podemos añadir un color en el fondo de la animación o podemos editar sus colores individualmente. Basta que presionemos descargar, y que pongamos el archivo en nuestro proyecto y podremos usar esta animación en Android.

![image](https://github.com/just5ebas/DispositivosMoviles/assets/105687611/6c4916a7-0c12-41bc-a6b9-63c91b27c17d)

**Para utilizar Lottie**

Debemos realizar esta implementación en el gradle de nuestra aplicación:

![image](https://github.com/just5ebas/DispositivosMoviles/assets/105687611/a05f3f12-d136-4f52-9106-aaade9e7bfc1)

Una vez realizado esto podremos añadir animaciones a nuestros layouts. Después de haber descargado alguna animación y tener el archivo json, lo que haremos será colocarlos en la carpeta raw dentro de la carpeta res. Ahora para que este aparezca en nuestra interfaz, lo que debemos hacer es utilizar el componente `com.airbnb.lottie.LottieAnimationView`:

![image](https://github.com/just5ebas/DispositivosMoviles/assets/105687611/869b678a-acd5-4c34-96b4-9fd12e724295)

Y como este es un componente adicional en nuestra interfaz, se le aplican las mismas reglas respecto al ancho y alto del componente, además de los constrains. Los nuevos atributos que trae este componente corresponden a:
- `lottie_autoPlay`: el cual inicia la animación automáticamente cuando su valor es `true`
- `lottie_loop`: este atributo ejecutará nuevamente la animación cuando esta finalice.
- `lottie_rawRes`: en este atributo se le indica cual es la animación que va a ejecutar.

![image](https://github.com/just5ebas/DispositivosMoviles/assets/105687611/7647b8d5-9cb6-472d-85c4-34561612c501)


## ¿Qué se puede hacer en la aplicación?
Además de disfrutar de las animaciones implementadas en toda la aplicación, podremos:
- Registrarnos en una base de datos (Firebase).
-	Iniciar sesión con nuestra cuenta registrada.
-	Recuperar nuestra contraseña.
-	Utilizar servicios de Google para grabarnos y transcribir lo que decimos.
-	Obtener nuestra ubicación en términos de latitud y longitud.
-	Autenticar nuestro acceso a la aplicación con la huella digital o con el PIN del teléfono.
-	Tomar fotos y mostrarlas.
-	Acceder a un API de Marvel completa con héroes y villanos de los comics.
-	Consultar personajes de Marvel que contengan una cadena de carácteres en su nombre (Para esto se debe presionar el boton de la lupa en el teclado).
-	Probar funcionalidades como enviar notificaciones, retornar resultados desde otra pantalla.
-	Probar animaciones de espera un tiempo determinado.
-	Cerrar toda la aplicación con un botón.

# Resultados
| ![image](https://github.com/just5ebas/DispositivosMoviles/assets/105687611/d979d9c1-5f22-4b8d-b50e-77ccaa160f1e) | ![image](https://github.com/just5ebas/DispositivosMoviles/assets/105687611/14738e50-e050-4d01-a384-ee0594e18219) |![image](https://github.com/just5ebas/DispositivosMoviles/assets/105687611/1be859a3-f39f-42d7-b878-87a58acfa383) |![image](https://github.com/just5ebas/DispositivosMoviles/assets/105687611/1172618a-b6fc-43a7-ba33-9f493a987fb2) |
|----------|:-------------:|:-------------:|:-------------:|

| ![image](https://github.com/just5ebas/DispositivosMoviles/assets/105687611/d945f74f-78fb-4d48-8a96-1e412e5de9ff) | ![image](https://github.com/just5ebas/DispositivosMoviles/assets/105687611/361b99fc-e95c-4cd3-b431-2aef0519b971) |![image](https://github.com/just5ebas/DispositivosMoviles/assets/105687611/5afbdabd-b1d6-4e45-a006-ee40e7cd7d73) |![image](https://github.com/just5ebas/DispositivosMoviles/assets/105687611/7a78cb2b-d554-4113-bb12-085391f16a2d) |
|----------|:-------------:|:-------------:|:-------------:|

| ![image](https://github.com/just5ebas/DispositivosMoviles/assets/105687611/d8506a9b-7fc2-41b4-a861-affb0d711932) | ![image](https://github.com/just5ebas/DispositivosMoviles/assets/105687611/ddd651ac-02c0-4d61-a282-534c616302e5) | ![image](https://github.com/just5ebas/DispositivosMoviles/assets/105687611/b070427e-4b60-401e-8a0d-ce130bdc1e07) |
|----------|:-------------:|:-------------:|

| ![image](https://github.com/just5ebas/DispositivosMoviles/assets/105687611/394566b8-037b-4590-b9c3-8cf47e3d9f1d) | ![image](https://github.com/just5ebas/DispositivosMoviles/assets/105687611/86c39b90-4443-4c6f-9554-c3e5c221b349) | ![image](https://github.com/just5ebas/DispositivosMoviles/assets/105687611/f47a9ec5-215c-4437-ad13-886b4d78149e) |
|----------|:-------------:|:-------------:|
