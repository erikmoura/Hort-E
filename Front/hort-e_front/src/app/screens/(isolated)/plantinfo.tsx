import { Ionicons as Icon } from '@expo/vector-icons';
import { useLocalSearchParams, useRouter } from "expo-router";
import React from 'react';
import { Dimensions, Image, ScrollView, StyleSheet, Text, TouchableOpacity, View } from "react-native";
import CampoPlanta from "../../components/CampoPlanta";

const { height, width } = Dimensions.get("window");

export default function PlantInfo() {
    const router = useRouter();
    const { id, nome, nomeCien, categoria, image, descricao, tipoSolo,
        irrigacao, localPlantio, clima, luzSolar, guias
    } = useLocalSearchParams();

    return (
        <View style={styles.container}>
            <View style={styles.bannerTopo}>
                <TouchableOpacity onPress ={() => router.back()}>
                    <Icon name="chevron-back" size={40} color="#fff" style={{marginTop: height * 0.0085}} />
                </TouchableOpacity>
                <Text style={styles.bannerText}> {nome} </Text>
            </View>
            <ScrollView style={styles.scrollView}> 
                <View style={styles.imageContainer}>
                    <Image
                        source={{ uri: image }}
                        style={styles.image}
                    />
                </View>
                <CampoPlanta
                    titulo="Nome científico:"
                    conteudo={nomeCien}
                />
                <CampoPlanta
                    titulo="Descrição:"
                    conteudo={descricao}
                />
                <CampoPlanta
                    titulo="Categoria:"
                    conteudo={categoria}
                />
                <CampoPlanta
                    titulo="Tipo de solo:"
                    conteudo={tipoSolo}
                />
                <CampoPlanta
                    titulo="Irrigação:"
                    conteudo={irrigacao}
                />
                <CampoPlanta
                    titulo="Local de plantio:"
                    conteudo={localPlantio}
                />
                <CampoPlanta
                    titulo="Clima:"
                    conteudo={clima}
                />
                <CampoPlanta
                    titulo="Luz solar:"
                    conteudo={luzSolar}
                />
                <View style={styles.blocoInvisivel} />
            </ScrollView>
        </View>
    )
}

const styles = StyleSheet.create({
    container: {
        flex: 1,
        alignItems: "center",
        justifyContent: "center",
        backgroundColor: "#F8F8F8",
    },
    bannerTopo: {
        position: 'absolute',
        top: 0,
        display: 'flex',
        flexDirection: 'row',
        backgroundColor: '#257D3C',
        paddingLeft: width * 0.02,
        width: '100%',
        height: height * 0.14,
        paddingTop: height * 0.055,
    },
    bannerText:{
        fontSize: 40,
        fontWeight: 'bold',
        color: '#FFFFFF',
    },
    scrollView:{
        marginTop: height * 0.14,
    },
    imageContainer: {
        marginTop: height * 0.02,
        width: width * 0.95,
        height: height * 0.20,
        borderRadius: 10,
        overflow: "hidden",
    },
    image:{
        width: "100%",
        height: "100%",
        resizeMode: "cover",
    },
    styleTitle: {
        fontSize: 40,
        fontWeight: "bold",
        color: "#000000",
        marginVertical: height * 0.02,
        marginHorizontal: width * 0.05,
    },
    styleContent: {
        fontSize: 16,
        fontWeight: "regular",
        color: "#000000",
        marginHorizontal: width * 0.05,
    },
    blocoInvisivel: {
        height: height * 0.12,
        width: '100%',
    },
    botaoVoltar: {
        position: "absolute",
        top: height * 0.06,
        left: width * 0.05,
        zIndex: 1,
    }
});