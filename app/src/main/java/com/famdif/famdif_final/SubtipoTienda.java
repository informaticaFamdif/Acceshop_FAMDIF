package com.famdif.famdif_final;

import java.util.ArrayList;
import java.util.List;

public class SubtipoTienda {
    private ArrayList<String> subtipoTienda= new ArrayList<>();

    public SubtipoTienda(String tipo){
        switch (tipo){
            case "Alimentación":
                subtipoTienda.add("Cualquiera");
                subtipoTienda.add("Carnicería");
                subtipoTienda.add("Charcutería");
                subtipoTienda.add("Comidas preparadas");
                subtipoTienda.add("Frutería");
                subtipoTienda.add("Panadería");
                subtipoTienda.add("24h");
                subtipoTienda.add("Quesería");
                subtipoTienda.add("Suministro de café");
                subtipoTienda.add("Suministro de té");
                subtipoTienda.add("Supermercado");
                subtipoTienda.add("Herbolario");
                subtipoTienda.add("Tienda de bebidas");
                subtipoTienda.add("Tienda de golosinas");
                subtipoTienda.add("Enoteca");
                subtipoTienda.add("Jamonería");
                subtipoTienda.add("Multiprecio");
                subtipoTienda.add("Pescadería");
                subtipoTienda.add("Mayorista de frutas y hortalizas");
                subtipoTienda.add("Alimentación ecológica");
                subtipoTienda.add("Tienda de especias");
                subtipoTienda.add("Mercado");
                subtipoTienda.add("Tienda de frutos secos");
                break;

            case "Restauración":
                subtipoTienda.add("Cualquiera");
                subtipoTienda.add("Bar");
                subtipoTienda.add("Cafetería");
                subtipoTienda.add("Confitería");
                subtipoTienda.add("Heladería");
                subtipoTienda.add("Pizzería");
                subtipoTienda.add("Restaurante");
                subtipoTienda.add("Bocatería");
                subtipoTienda.add("Hamburguesería");
                subtipoTienda.add("Asadero");
                subtipoTienda.add("Kebab");
                subtipoTienda.add("Mesón");
                subtipoTienda.add("Maquinaria para hostelería");
                subtipoTienda.add("Salón de té");
                subtipoTienda.add("Comidas preparadas");
                break;

            case "Educación y auxiliares":
                subtipoTienda.add("Cualquiera");
                subtipoTienda.add("Academia");
                subtipoTienda.add("Autoescuela");
                subtipoTienda.add("Centro de formación");
                subtipoTienda.add("Copistería");
                subtipoTienda.add("Librería");
                subtipoTienda.add("Papelería");
                subtipoTienda.add("Editorial");
                subtipoTienda.add("Imprenta");
                subtipoTienda.add("Tienda de prensa y revistas");
                subtipoTienda.add("Escuela infantil");
                subtipoTienda.add("Material de oficina");
                break;

            case "Salud":
                subtipoTienda.add("Cualquiera");
                subtipoTienda.add("Clínica auditiva");
                subtipoTienda.add("Clínica dental");
                subtipoTienda.add("Farmacia");
                subtipoTienda.add("Oftalmólogo");
                subtipoTienda.add("Óptica");
                subtipoTienda.add("Parafarmacia");
                subtipoTienda.add("Clínica de fisioterapia");
                subtipoTienda.add("Centro médico");
                subtipoTienda.add("Tienda de nutrición deportiva");
                subtipoTienda.add("Centro de salud");
                subtipoTienda.add("Nutricionista");
                subtipoTienda.add("Laboratorio");
                subtipoTienda.add("Ginecólogo");
                subtipoTienda.add("Medicina alternativa / Homeopatía");
                subtipoTienda.add("Podólogo");
                subtipoTienda.add("Clínica de psicología");
                subtipoTienda.add("Medicina estética");
                subtipoTienda.add("Centro de diagnóstico");
                subtipoTienda.add("Clínica pediatrica");
                subtipoTienda.add("Acupuntura");
                subtipoTienda.add("Hipnoterapia");
                subtipoTienda.add("Logopedia");
                subtipoTienda.add("Material médico");
                break;

            case "Estética y cuidado personal":
                subtipoTienda.add("Cualquiera");
                subtipoTienda.add("Centro de estética");
                subtipoTienda.add("Entrenador personal");
                subtipoTienda.add("Gabinete de estética");
                subtipoTienda.add("Gimnasio");
                subtipoTienda.add("Ortopedia");
                subtipoTienda.add("Peluquería");
                subtipoTienda.add("Perfumería");
                subtipoTienda.add("Salón de belleza");
                subtipoTienda.add("Tienda de cosméticos");
                subtipoTienda.add("Podólogo");
                subtipoTienda.add("Estilista de uñas");
                subtipoTienda.add("Estudio de tatuajes");
                subtipoTienda.add("Barbería");
                subtipoTienda.add("Centro de yoga");
                subtipoTienda.add("Masajes");
                subtipoTienda.add("Centro de pediculosis");
                subtipoTienda.add("Centro de autobronceado");
                subtipoTienda.add("Estética oncológica");
                break;

            case "Textil y complementos":
                subtipoTienda.add("Cualquiera");
                subtipoTienda.add("Accesorios de moda");
                subtipoTienda.add("Bisutería");
                subtipoTienda.add("Corsetería");
                subtipoTienda.add("Joyería");
                subtipoTienda.add("Mercería");
                subtipoTienda.add("Sombrerería");
                subtipoTienda.add("Textil");
                subtipoTienda.add("Tienda de bolsos y maletas");
                subtipoTienda.add("Tienda de complementos");
                subtipoTienda.add("Tienda de deportes");
                subtipoTienda.add("Tienda de ropa");
                subtipoTienda.add("Tienda de ropa de hombre");
                subtipoTienda.add("Tienda de ropa de mujer");
                subtipoTienda.add("Tienda de ropa infantil");
                subtipoTienda.add("Tienda de trajes de novia");
                subtipoTienda.add("Tintorería");
                subtipoTienda.add("Zapatería");
                subtipoTienda.add("Zapatería infantil");
                subtipoTienda.add("Tienda de uniformes");
                subtipoTienda.add("Tienda de ropa tradicional");
                subtipoTienda.add("Tienda de ropa urbana");
                subtipoTienda.add("Peletería");
                subtipoTienda.add("Máquinas de coser");
                subtipoTienda.add("Modista");
                subtipoTienda.add("Reparación de calzado");
                subtipoTienda.add("Relojería");
                subtipoTienda.add("Sastrería");
                break;

            case "Ocio y entretenimiento":
                subtipoTienda.add("Cualquiera");
                subtipoTienda.add("Apuestas");
                subtipoTienda.add("Bingo");
                subtipoTienda.add("Loterias y apuestas");
                subtipoTienda.add("Sala de juegos");
                subtipoTienda.add("Tienda de juguetes");
                subtipoTienda.add("Tienda de regalos");
                subtipoTienda.add("Tienda de coleccionismo");
                subtipoTienda.add("Club");
                subtipoTienda.add("Pub");
                subtipoTienda.add("Discoteca");
                subtipoTienda.add("Tienda de instrumentos");
                subtipoTienda.add("Tienda de juegos");
                subtipoTienda.add("Tienda de discos");
                subtipoTienda.add("Organización de eventos");
                subtipoTienda.add("Teatro");
                subtipoTienda.add("Armería");
                subtipoTienda.add("Tienda de tarot");
                subtipoTienda.add("Sex shop");
                subtipoTienda.add("Rocódromo");
                subtipoTienda.add("Meditación");
                subtipoTienda.add("show room");
                break;

            case "Bancos, seguros y gestoría":
                subtipoTienda.add("Cualquiera");
                subtipoTienda.add("Aseguradora");
                subtipoTienda.add("Asesoría");
                subtipoTienda.add("Bancos");
                subtipoTienda.add("Correduría de seguros");
                subtipoTienda.add("Institución financiera");
                subtipoTienda.add("Administración de fincas");
                subtipoTienda.add("Intermediación");
                subtipoTienda.add("Abogados");
                subtipoTienda.add("Empresa de trabajo temporal");
                subtipoTienda.add("Envíos monetarios");
                subtipoTienda.add("Oficinas");
                break;

            case "Inmobiliario":
                subtipoTienda.add("Cualquiera");
                subtipoTienda.add("Constructora");
                subtipoTienda.add("Inmobiliaria");
                subtipoTienda.add("Instalaciones y mantenimiento");
                subtipoTienda.add("Artículos de fontanería");
                subtipoTienda.add("Instalaciones de climatización");
                subtipoTienda.add("Estudio de arquitectura");
                subtipoTienda.add("Fontanero");
                subtipoTienda.add("Pinturas");
                subtipoTienda.add("Reformas");
                subtipoTienda.add("Estudio de interiorismo");
                subtipoTienda.add("Consultoría");
                subtipoTienda.add("Cerrajero");
                subtipoTienda.add("Carpintería");
                subtipoTienda.add("Persianas");
                subtipoTienda.add("Estudio de ingeniería");
                subtipoTienda.add("Toldos");
                subtipoTienda.add("Diseño 3D");
                subtipoTienda.add("Cocinas");
                break;

            case "Hogar y decoración":
                subtipoTienda.add("Cualquiera");
                subtipoTienda.add("Artículos para el hogar");
                subtipoTienda.add("Colchonería");
                subtipoTienda.add("Cuchillería");
                subtipoTienda.add("Decoración");
                subtipoTienda.add("Electrodomésticos");
                subtipoTienda.add("Ferretería");
                subtipoTienda.add("Iluminación");
                subtipoTienda.add("Tienda de muebles");
                subtipoTienda.add("Tienda de enmarcación");
                subtipoTienda.add("Bazar");
                subtipoTienda.add("Tienda de artículos infantiles");
                subtipoTienda.add("Jardinería");
                subtipoTienda.add("Mudanzas");
                subtipoTienda.add("Floristería");
                subtipoTienda.add("Tienda de artículos de segunda mano");
                subtipoTienda.add("Taller de artesanía");
                subtipoTienda.add("Tienda de cortinas");
                subtipoTienda.add("Servicio técnico");
                break;

            case "Tecnología y telecomunicaciones":
                subtipoTienda.add("Cualquiera");
                subtipoTienda.add("Fotografía");
                subtipoTienda.add("Proveedor de internet");
                subtipoTienda.add("Tienda de accesorios para móviles");
                subtipoTienda.add("Tienda de cartuchos para impresoras");
                subtipoTienda.add("Tienda de informática");
                subtipoTienda.add("Tienda de telefonía");
                subtipoTienda.add("Contratista de telecomunicaciones");
                subtipoTienda.add("Locutorio");
                subtipoTienda.add("Tienda de reparación de móviles");
                subtipoTienda.add("Tienda de máquinas registradoras");
                subtipoTienda.add("Tienda de electrónica");
                subtipoTienda.add("Control de accesos");
                subtipoTienda.add("Servicio técnico");
                subtipoTienda.add("Productor audiovisual");
                subtipoTienda.add("Empresa de software");
                subtipoTienda.add("Tienda de maquinaría");
                break;

            case "Automoción":
                subtipoTienda.add("Cualquiera");
                subtipoTienda.add("Cristales para automóviles");
                subtipoTienda.add("Lavadero");
                subtipoTienda.add("Taller");
                subtipoTienda.add("Tienda de recambios");
                subtipoTienda.add("Taller de bicicletas");
                subtipoTienda.add("Taller de reparación de motos");
                subtipoTienda.add("Neumáticos");
                subtipoTienda.add("Matrículas");
                subtipoTienda.add("Estación de servicio");
                subtipoTienda.add("Tienda de bicicletas");
                subtipoTienda.add("Concesionario");
                subtipoTienda.add("Concesionario de motos");
                subtipoTienda.add("Alquiler de vehiculos");
                break;

            case "Residencial Público":
                subtipoTienda.add("Cualquiera");
                subtipoTienda.add("Hotel");
                subtipoTienda.add("Apartamentos");
                subtipoTienda.add("Alquiler de apartamentos");
                break;

            case "Arte":
                subtipoTienda.add("Cualquiera");
                subtipoTienda.add("Estudio de pintura");
                subtipoTienda.add("Galería de arte");
                subtipoTienda.add("Diseñador gráfico");
                break;

            case "Viajes y transportes":
                subtipoTienda.add("Cualquiera");
                subtipoTienda.add("Infraestructura de transporte");
                subtipoTienda.add("Turoperadora");
                subtipoTienda.add("Agencia de viajes");
                subtipoTienda.add("Correos");
                subtipoTienda.add("Transporte de viajeros");
                subtipoTienda.add("Envíos");
                subtipoTienda.add("Envíos monetarios");
                break;


            case "Otros":
                subtipoTienda.add("Cualquiera");
                subtipoTienda.add("ONG");
                subtipoTienda.add("Estanco");
                subtipoTienda.add("Tienda de vaporizadores");
                subtipoTienda.add("Droguería");
                subtipoTienda.add("Centro comercial");
                subtipoTienda.add("Mascotas");
                subtipoTienda.add("Veterinario");
                subtipoTienda.add("Cofradía");
                subtipoTienda.add("Centro social");
                subtipoTienda.add("Otros");
                subtipoTienda.add("Lavandería");
                subtipoTienda.add("Funeraria");
                subtipoTienda.add("Proveedor de semillas");
                subtipoTienda.add("Tienda de productos agrícolas");
                subtipoTienda.add("Cereales");
                subtipoTienda.add("Representación comercial");
                subtipoTienda.add("Productos siderúrgicos");
                subtipoTienda.add("Agencia de publicidad");
                subtipoTienda.add("Trabajo social");
                subtipoTienda.add("Centro de día");
                break;

        }

    }

    public List<String> getSubTiposTienda() {
        return subtipoTienda;
    }

}
