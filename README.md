![Networks](https://cdn.jsdelivr.net/gh/SlimefunGuguProject/Networks@master/images/logo/logo_large.png)

# Networks Expansion — fork de DrakesCraft

Sistema de almacenamiento y transporte de objetos para Slimefun, al estilo de Applied Energistics
o Refined Storage. Este repositorio es el fork de DrakesCraft de
[balugaq/NetworksExpansion](https://github.com/balugaq/NetworksExpansion), que a su vez amplía
[Networks de Sefiraat](https://github.com/Sefiraat/Networks).

> **No necesita Networks instalado.** Trae la base dentro, así que **sustituye** a
> NetworksV6-Drake, no lo acompaña. Tener los dos a la vez registraría los mismos ítems dos veces.

## De dónde viene cada cosa

| Capa | Autoría | Qué aporta |
|---|---|---|
| Base | [Sefiraat](https://github.com/Sefiraat/Networks) | Red, puentes, celdas cuánticas, rejilla, cargo |
| Expansión | [balugaq](https://github.com/balugaq/NetworksExpansion) y ytdd9527 | ~34.000 líneas: las máquinas de abajo |
| Este fork | DrakesCraft-Labs | Arreglos propios y pruebas, bajo `cl.jackstar` |

Todo es **GPL-3.0**. Los ficheros heredados conservan su paquete y su cabecera de copyright: lo
exige la licencia y además es lo correcto con quien hizo el trabajo. Lo nuestro va aparte, en
`cl.jackstar`, para que siempre se sepa qué es de quién.

## Qué cambiamos nosotros

### Arreglada la fuga del caché de direcciones

`SELECTED_DIRECTION_MAP` es un `HashMap` estático con tres escrituras y **ninguna limpieza**. La
fuga de memoria era lo de menos: `getSelectedFace()` consulta ese mapa **antes** que al
BlockStorage, así que una entrada vieja se imponía sobre la dirección real de un bloque nuevo.

En la práctica: colocabas un nodo direccional donde antes hubo otro y **apuntaba a donde tú no
habías elegido**, sin ningún aviso. Con el tiempo el mapa crecía con cada nodo colocado en la
historia del servidor y solo se vaciaba al reiniciar.

El arreglo cuelga de la rotura del bloque y vive en la clase base `NetworkDirectional`, así que
las 72 máquinas de la expansión quedan cubiertas de una vez: todas heredan de ahí vía
`AdvancedDirectional`.

### Primeras pruebas del proyecto

El repositorio original no tenía ninguna, así que nada impedía que un arreglo se deshiciera sin
enterarse. Las nuestras están en `src/test/java/cl/jackstar/` y son **estructurales sobre el
fuente**: montar un nodo direccional completo exige media red en pie, y lo que hay que garantizar
aquí es que exista una limpieza enganchada a la rotura del bloque.

### Compilación accesible desde Chile

El wrapper de Gradle apuntaba a un espejo de Tencent inalcanzable desde aquí. Cambiado al origen
oficial de Gradle.

## Qué trae la expansión

### Cargo — transferencia dirigida (29 máquinas)

Sustituyen al cargo básico de Slimefun con transferencia punto a punto y en línea.

- **Transfer / LineTransfer** — mueven objetos entre dos puntos, o a lo largo de una línea recta.
  La versión en línea evita tener que encadenar nodos uno a uno.
- **Grabber / Pusher** — extraen de un contenedor hacia la red, o empujan de la red al contenedor.
- **VanillaGrabber / VanillaPusher** — lo mismo con cofres y contenedores normales, sin necesidad
  de que sean bloques de Slimefun.
- **BestPusher / MorePusher** — variantes de reparto: la primera elige el destino más adecuado, la
  segunda reparte entre varios.
- **Whitelisted…** — versiones con filtro: solo mueven lo que esté en su lista.
- **Advanced…** — cada familia tiene versión avanzada, con más alcance y velocidad.
- **LinePowerOutlet** — distribuye energía en línea, igual que las anteriores hacen con objetos.

### Red — control y monitorización (19 máquinas)

- **NetworkGridNewStyle / HangingGridNewStyle** — la rejilla para ver y sacar todo el contenido de
  la red desde una sola interfaz. La colgante ocupa menos espacio.
- **NetworkCraftingGrid / SmartNetworkCraftingGrid** — rejilla con crafteo integrado; la inteligente
  saca los materiales de la red automáticamente.
- **AdvancedImport / AdvancedExport** — entrada y salida de la red con más caudal.
- **SmartGrabber / SmartPusher** — versiones que deciden origen y destino por sí solas.
- **AdvancedVacuum** — recoge objetos del suelo hacia la red.
- **AdvancedPurger / SuperTrash** — destruyen lo que sobra, con filtro.
- **AdvancedGreedyBlock** — acapara un tipo de objeto concreto.
- **AdvancedWirelessTransmitter** — enlaza secciones de red sin puentes físicos.
- **NetworkInputOnlyMonitor / OutputOnlyMonitor / SwitchingMonitor** — monitores de un solo sentido
  o conmutables, para ver el flujo sin exponer toda la red.
- **NetworkBlueprintDecoder** — lee planos de crafteo.
- **DueMachine**, **Offsetter** — utilidades de colocación y desfase.

### Gestión (4) y unidades (2)

- **CrafterManager / DrawerManager / QuantumManager** — paneles para administrar en bloque todos
  los crafteadores, cajones o celdas cuánticas de la red, en vez de ir uno por uno.
- **NetworksDrawer** — cajón de almacenamiento masivo de un solo tipo.

### Herramientas manuales (5)

- **ExpansionWorkbench** — banco de trabajo de la expansión.
- **FacingPresetter** — fija la dirección de un nodo sin tener que abrirlo.
- **ItemDifferenter** — distingue variantes de un mismo objeto.
- **StorageCardConverter** — convierte entre formatos de tarjeta de almacenamiento.
- **StorageUnitUpgradeTable** — mejora unidades de almacenamiento.

### Visores (1)

- **ItemFlowViewer** — muestra el flujo de objetos por la red. Es la herramienta para depurar una
  red que no hace lo que esperas.

## Estado de compatibilidad

> **Pendiente.** El proyecto original compila contra `paper-api:1.21.4` y `Slimefun4:2025.1`.
> DrakesCraft corre **Purpur 1.21.11**, así que la actualización de dependencias está por hacer.
> Compila y las pruebas pasan, pero **todavía no se ha validado en un servidor 1.21.11**.

## Documentación original

El wiki de los ítems de la base está en
[slimefun-addons-wiki.guizhanss.cn/networks](https://slimefun-addons-wiki.guizhanss.cn/networks/)
(en chino).

## Agradecimientos

Traducidos del README original, porque el crédito es de quien lo hizo:

> Gracias a **Boomer**, **Cai** y **Lucky** por ayudar a probar y pulir el plugin.
>
> Gracias al administrador de **mct.tantrum.org** por proporcionar el entorno de pruebas.
>
> Gracias a **GentlemanCheesy**, de **mc.talosmp.net**, por ser el primer patrocinador de
> [Sefiraat](https://github.com/Sefiraat). Un café más al mes da ánimos para seguir programando.
>
> Gracias a [m1919810 / matl114](https://github.com/m1919810) por ayudar a
> [balugaq](https://github.com/balugaq) a encontrar varios fallos de Networks Expansion.

Y gracias a la comunidad de **Slimefun Gugu Project** y a **balugaq**, cuyo trabajo es la mayor
parte de este repositorio.

## Licencia

GPL-3.0, como el original. Cualquier derivado también lo es y su fuente se publica.
