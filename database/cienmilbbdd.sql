-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Dec 09, 2025 at 08:35 PM
-- Server version: 10.4.32-MariaDB
-- PHP Version: 8.0.30

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `cienmilbbdd`
--

-- --------------------------------------------------------

--
-- Table structure for table `detalle_pedido`
--

CREATE TABLE `detalle_pedido` (
  `id` bigint(20) NOT NULL,
  `cantidad` int(11) NOT NULL,
  `mensaje_personalizado` varchar(255) DEFAULT NULL,
  `precio_unitario` decimal(10,2) NOT NULL,
  `pedido_id` bigint(20) NOT NULL,
  `producto_id` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `pedidos`
--

CREATE TABLE `pedidos` (
  `id` bigint(20) NOT NULL,
  `comuna_envio` enum('ALGARROBO','ALHUEL','ALTO_DEL_CARMEN','ALTO_HOSPICIO','ANCUD','ANDACOLLO','ANGOL','ANTARTICA','ANTOFAGASTA','ARICA','BUIN','BULNES','CABILDO','CABO_DE_HORNOS','CALAMA','CALBUCO','CALDERA','CALERA_DE_TANGO','CALLE_LARGA','CAMARONES','CAMINNA','CAMPO_DE_HIELO_SUR','CANELA','CARAHUE','CARAHUE2','CARTAGENA','CASABLANCA','CASTRO','CATEMU','CAUQUENES','CERRILLOS','CHAÑARAL','CHIGUAYANTE','CHILE_CHICO','CHILLAN','CHIMBARONGO','CHONCHI','CISELLO','COELEMU','COIHUECO','COINCO','COLBUN','COLCHANE','COLINA','COLLIPULLI','COLTAUCO','COMBARBALA','CONCEPCION','CONCON','COPIAPO','COQUIMBO','CORONEL','CORRAL','COYHAIQUE','CURACAVI','CURICO','CURICO2','DIEGO_DE_ALMAGRO','DOÑIHUE','Dalcahue','EL_CARMEN','EL_MONTE','EL_QUISCO','EL_TABO','ESTACION_CENTRAL','FREIRE','FREIRINA','FRUTILLAR','FUTRONO','GENERAL_LAGOS','GORBEA','GRANEROS','GUAITECAS','HIJUELAS','HUALAÑE','HUALQUI','HUARA','HUASCO','HUECHURABA','ILLAPEL','INDEPENDENCIA','IQUIQUE','ISLA_DE_MAIPO','ISLA_DE_PASCUA','JUAN_FERNANDEZ','LAGO_RANCO','LAGO_VERDE','LAGUNA_BLANCA','LAMPA','LAS_CABRAS','LAS_CONDES','LAUTARO','LA_CALERA','LA_CISTERNA','LA_CRUZ','LA_ESTRELLA','LA_FLORIDA','LA_HIGUERA','LA_LIGUA','LA_SERENA','LA_UNION','LIMACHE','LINARES','LINARES2','LLANQUIHUE','LLAYLLA','LONCOCHE','LONGAVI','LOS_ANDES','LOS_ANGELES','LOS_LAGOS','LOS_VILOS','LOTA','LO_ESPEJO','LO_PRADO','MACHALI','MAIPU','MARCHIGUE','MARIA_ELENA','MARIA_PINTO','MARIQUINA','MAULE2','MEJILLONES','MELINKA','MELIPEUCO','MELIPILLA','MONTE_PATRIA','NACIMIENTO','NANCAGUA','NINHUE','NOGALES','NUEVA_IMPERIAL','NUÑOA','OHIGGINS2','OLIVAR','OLLAGUE','OLMUE','OSORNO','OVALLA','PADRE_HURTADO','PADRE_LAS_CASAS','PAIGUANO','PAINE','PALMILLA','PANGUIPULLI','PANQUEHUE','PAPUDO','PARRAL','PEMUCO','PENAFLOR','PENNALOLEN','PERALILLO','PETORCA','PEUMO','PICA','PICHILEMU','PIRQUE','PORTEZUELO','PORVENIR','POZO_ALMONTE','PRIMAVERA','PROVIDENCIA','PUCHUN','PUCHUNCAVI','PUCON','PUDAHUEL','PUENTE_ALTO','PUERTO_AYSEN','PUERTO_MONTT','PUERTO_OCTAY','PUERTO_VARAS','PUMANQUE','PUNITAQUI','PUNTA_ARENAS','PUTAENDO','PUTRE','Paillaco','QUEILEN','QUELLON','QUEMCHI','QUILICURA','QUILLOTA','QUILLOTA2','QUILPUE','QUINTA_NORMAL','QUINTERO','QUIRIHUE','RANCAGUA','RANQUIL','RECOLETA','RENAICO','RENCA','RETIRO','RINCONADA','RIO_BUENO','RIO_CLARO','RIO_CLARO2','RIO_HURTADO','RIO_IBANEZ','RIO_VERDE','ROMERAL','SALAMANCA','SANTA_BARBARA','SANTA_CRUZ','SANTA_MARIA','SANTIAGO','SANTO_DOMINGO','SAN_ANTONIO','SAN_BERNARDO','SAN_CARLOS','SAN_CLEMENTE','SAN_ESTEBAN','SAN_FELIPE','SAN_FERNANDO','SAN_FRANCISCO_DE_MOSTAZAL','SAN_GREGORIO','SAN_IBAN','SAN_JAVIER','SAN_JOAQUIN','SAN_JOSE_DE_LA_MARIQUINA','SAN_JOSE_DE_MAIPO','SAN_MIGUEL','SAN_NICOLAS','SAN_PEDRO','SAN_PEDRO_DE_ATACAMA','SAN_PEDRO_DE_LA_PAZ','SAN_RAFAEL','SAN_RAMON','SAN_VICENTE','SIERRA_GORDA','TALA','TALAGANTE','TALCA','TALCAHUANO','TALTAL','TEMUCO','TIERRA_AMARILLA','TIL_TIL','TOCOPILLA','TORRES_DEL_PAINE','TORTEL','TREHUACO','TUCAPEL','VALDIVIA','VALLENAR','VALPARAISO','VICHUQUEN','VICHUQUEN2','VICTORIA','VILCUN','VILLA_ALEGRE','VILLA_ALEMANA','VINA_DEL_MAR','VITACURA','YERBAS_BUENAS','YUMBEL','YUMBEL2','YUNGAY','ZAPALLAR') NOT NULL,
  `direccion_envio` varchar(255) NOT NULL,
  `estado` enum('CANCELADO','CONFIRMADO','ENTREGADO','EN_CAMINO','EN_PREPARACION','LISTO_PARA_ENVIO','PENDIENTE') NOT NULL,
  `fecha` datetime(6) NOT NULL,
  `iva` decimal(14,2) NOT NULL,
  `numero_pedido` varchar(255) NOT NULL,
  `region_envio` enum('ANTOFAGASTA','ARAUCANIA','ARICA','ATACAMA','AYSEN','BIOBIO','COQUIMBO','LOS_LAGOS','LOS_RIOS','MAGALLANES','MAULE','NUBLE','OHIGGINS','REGION_METROPOLITANA','TARAPACA','VALPARAISO') NOT NULL,
  `subtotal` decimal(14,2) NOT NULL,
  `total` decimal(14,2) NOT NULL,
  `usuario_id` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `producto`
--

CREATE TABLE `producto` (
  `codigo` varchar(255) NOT NULL,
  `categoria` varchar(50) DEFAULT NULL,
  `descripcion` varchar(500) DEFAULT NULL,
  `imagen_url` varchar(200) DEFAULT NULL,
  `nombre` varchar(100) DEFAULT NULL,
  `precio` decimal(10,2) DEFAULT NULL,
  `stock` int(11) DEFAULT NULL,
  `visible` bit(1) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `producto`
--

INSERT INTO `producto` (`codigo`, `categoria`, `descripcion`, `imagen_url`, `nombre`, `precio`, `stock`, `visible`) VALUES
('TC001', 'Tortas Cuadradas', NULL, 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcROZ5sqI_unxOBvVGlZHFqxnv0gMT-bQO4cZQ&s', 'Thortita', 45000.00, 15, b'1');

-- --------------------------------------------------------

--
-- Table structure for table `usuario`
--

CREATE TABLE `usuario` (
  `run` varchar(255) NOT NULL,
  `apellidos` varchar(50) DEFAULT NULL,
  `comuna` varchar(50) DEFAULT NULL,
  `correo` varchar(255) DEFAULT NULL,
  `direccion` varchar(100) DEFAULT NULL,
  `nombre` varchar(50) DEFAULT NULL,
  `password` varchar(100) DEFAULT NULL,
  `region` varchar(50) DEFAULT NULL,
  `role` varchar(50) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `usuario`
--

INSERT INTO `usuario` (`run`, `apellidos`, `comuna`, `correo`, `direccion`, `nombre`, `password`, `region`, `role`) VALUES
('11111111-1', '', 'SANTIAGO', 'alan@gmail.com', 'alan', 'alan', 'alan123', 'REGION_METROPOLITANA', 'cliente'),
('124823226', 'line', 'ARICA', 'kline@gmail.com', 'string', 'karen', '1234567', 'REGION_METROPOLITANA', 'string'),
('admin123', 'admin123', 'SANTIAGO', 'admin123@gmail.com', 'admin123', 'admin123', 'admin123', 'REGION_METROPOLITANA', 'admin');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `detalle_pedido`
--
ALTER TABLE `detalle_pedido`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKg9h17fjynh9lgf1kbn0v9p4kf` (`pedido_id`),
  ADD KEY `FK2yc3nts8mdyqf6dw6ndosk67a` (`producto_id`);

--
-- Indexes for table `pedidos`
--
ALTER TABLE `pedidos`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UK6ywv5771tibn93splh6t3ft06` (`numero_pedido`),
  ADD KEY `FKonf32qpq8pb2950dfgiyevy1h` (`usuario_id`);

--
-- Indexes for table `producto`
--
ALTER TABLE `producto`
  ADD PRIMARY KEY (`codigo`);

--
-- Indexes for table `usuario`
--
ALTER TABLE `usuario`
  ADD PRIMARY KEY (`run`),
  ADD UNIQUE KEY `UK2mlfr087gb1ce55f2j87o74t` (`correo`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `detalle_pedido`
--
ALTER TABLE `detalle_pedido`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `pedidos`
--
ALTER TABLE `pedidos`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `detalle_pedido`
--
ALTER TABLE `detalle_pedido`
  ADD CONSTRAINT `FK2yc3nts8mdyqf6dw6ndosk67a` FOREIGN KEY (`producto_id`) REFERENCES `producto` (`codigo`),
  ADD CONSTRAINT `FKg9h17fjynh9lgf1kbn0v9p4kf` FOREIGN KEY (`pedido_id`) REFERENCES `pedidos` (`id`);

--
-- Constraints for table `pedidos`
--
ALTER TABLE `pedidos`
  ADD CONSTRAINT `FKonf32qpq8pb2950dfgiyevy1h` FOREIGN KEY (`usuario_id`) REFERENCES `usuario` (`run`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
