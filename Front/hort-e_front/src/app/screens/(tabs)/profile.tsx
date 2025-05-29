import React from "react";
import { View, StyleSheet, Dimensions, ScrollView } from "react-native";
import { useRouter } from "expo-router";
import InputPerfil from "../../components/InputPerfil";
import BannerPerfil from "../../components/BannerPerfil";
import FotoPerfil from "../../components/FotoPerfil";

const { height, width } = Dimensions.get("window");

export default function Profile() {

    return (
        <View style={styles.container}>
            <View style={styles.bannerVerde}>
                <BannerPerfil />
            </View>
            <View style = {styles.fotoEdit}>
                <FotoPerfil />
            </View>
            <ScrollView style={styles.scrollview}>
                <InputPerfil
                titulo="Nome:"
                label="Digite seu nome..."
                onPress={() => {}}
                />
                <InputPerfil
                    titulo="Cidade:"
                    label="Digite sua cidade..."
                    onPress={() => {}}
                />
                <InputPerfil
                    titulo="Email:"
                    label="Digite seu email..."
                    onPress={() => {}}
                />
                <InputPerfil
                    titulo="Logradouro:"
                    label="Digite seu logradouro..."
                    onPress={() => {}}
                />
                <InputPerfil
                    titulo="Interesses:"
                    label="Digite seus interesses..."
                    onPress={() => {}}
                />
                <View style={styles.blocoInvisivel}/>
            </ScrollView>
        </View>

    );
};

const styles = StyleSheet.create({
    container: {
        flex: 1,
        backgroundColor: "#F8F8F8",
        justifyContent: "center",
        alignItems: "center",
    },
    scrollview: {
        marginTop: height * 0.25
    },
    blocoInvisivel: {
        height: height * 0.12,
        width: '100%'
    },
    bannerVerde: {
        position: 'absolute',
        top: 0,
        width: '100%',
    },
    fotoEdit: {
        position: 'absolute',
        top: height * 0.05,
    }
})
