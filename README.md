# Not-Virus
---
## El juego de cartas _Virus!_ ahora para Android
\- Software **NO** oficial

\- Virus! es un producto de [**TRANJIS GAMES**](https://tranjisgames.com/)

### - Creadores:
- Carlos López
- [Domingo Cabrero](https://www.linkedin.com/in/domingocabrero/)
- [Santi Santisteban](https://www.instagram.com/sanzellos/)
### - Ilustraciones:
- [David GJ](https://www.instagram.com/davidgj/)
---
Esta app corresponde al proyecto final del Bootcamp de "Desarrollo para Android con lenguaje Kotlin"
impartido por UNAB (Chile).

Se decidió optar por recrear este juego debido al gusto personal por los juegos de mesa,
siendo **_Virus!_** uno de mis favoritos.

---
### Android:
- minSdk: 26
- targetSdk: 36

Lenguaje: [**Kotlin**](https://kotlinlang.org/docs/getting-started.html) "2.2.21"

Interfaz gráfica (UI): [**Jetpack Compose**](https://www.jetpackcompose.pro/home/guide/)

Guardado de partida local: [**Room**](https://developer.android.com/jetpack/androidx/releases/room) "2.8.2"

Patrón de diseño: **MVVM** (*Modelo-Vista-ViewModel*)

---

## Descripción del Juego:

- El juego cuenta con **68** cartas. 
- Cada jugador cuenta con una mano compuesta por **3** cartas.
- Cada jugador cuenta con una zona donde colocar las cartas tipo ORGANO que se denominará _cuerpo_.
- En el centro de la zona de juego se mantendrá la _baraja_ de la cual se robaran cartas en cada turno.
- A su vez junto a la _baraja_ se tendrá la _pila de descarte_.

El juego cuenta con **4** tipos de cartas:
1. Organo
2. Virus
3. Medicina
4. Tratamiento

Cada Organo, Virus y Medicina tiene **5** variaciones que se distinguen por su color:
1. Amarillo
2. Azul
3. Rojo
4. Verde
5. Multicolor

Mientras que los Tratamientos NO tienen color asociado. 

---
El objetivo es contar con 4 Organos sanos para considerarse victorioso,
pudiendo tener hasta 5 ORGANOS en el _cuerpo_.

- Los Virus tienen la función de _infectar_ a los organos.
  - Al aplicar 2 _infecciones_ sobre un Organo este se _extirpa_.
  - Al aplicar un Virus sobre una Medicina esta se _neutraliza_ 
    descartandose tanto el Virus como la Medicina.

- Las Medicinas tienen la función de _vacunar_ a los organos.
  - Al aplicar 2 _vacunas_ sobre un Organo este se _inmuniza_.
  - Al aplicar una Medicina sobre un Virus esta se _cura_
    descartandose tanto el Virus como la Medicina.
 
- Cada Virus y Medicina solo puede afectar a un Organo de su mismo color,
siendo excepciones de esto aquellos que sean Multicolor:
  - Un Organo Multicolor puede ser afectado por Virus o Medicinas de cualquier color.
  - Un Virus Multicolor puede actuar sobre un Organo o Medicina de cualquier color.
  - Una Medicina Multicolor puede actuar sobre un Organo o Virus de cualquier color.

- Un Organo _inmune_ no puede ser _infectado_ por un Virus o afectado por un Tratamiento 
a menos que la carta indique lo contrario.

- Los Tratamientos generan acciones sobre el _cuerpo_ o la mano de los jugadores.

- En cada turno el jugador tendrá la opción de jugar 1 carta
o descartar de su mano tantas cartas como desee.

- Al finalizar el turno el jugador debe robar tantas cartas como sea necesario
para finalizar con 3 cartas en su mano.

---
***Fuente: [TranjisGames-Virus!](https://tranjisgames.com/shop/trg-001vir-virus-1104)***

---
## Descripción de lógica:

Se considera que cada jugador parte estando a **4** turnos de la victoria.

Cada vez que se agrega un Organo al _cuerpo_ el jugador avanza un turno a la victoria.
Mientras que si este se ve infectado o es _extirpado_ retrocede un turno.

Se considerará que un jugador es victorioso al momento que
dicho número sea **menor o igual** a **0** (**n <= 0**),
ya que esto indicará que hay al menos 4 Organos sanos en su _cuerpo_. 

---

## Implementar:
- [ ] Registro/Sesión de Usuario
- [x] Menú de selección de dificultad
- [x] Interfaz para 1 jugador
  - [ ] mejoras UI
- [x] Interfaz para 2 jugadores (Local)
  - [ ] mejoras UI
- [ ] Funciones de TRATAMIENTOS (1/5)
- [x] IA nivel Básico
- [ ] IA nivel Medio
- [ ] IA nivel Difícil
- [ ] IA nivel Competitiva
- [ ] Tutorial
- [ ] Disclaimer de derechos
- [ ] Implementacion Partida _on-line_ **PvP**
- [ ]