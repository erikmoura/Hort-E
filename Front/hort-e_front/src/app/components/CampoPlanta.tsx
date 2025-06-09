import React from 'react';
import { Dimensions, StyleSheet, Text, View } from 'react-native';

const { height, width } = Dimensions.get('window');

type propsCampoPlanta = {
    titulo: string;
    conteudo: string;
}

export default function CampoPlanta({titulo, conteudo}: propsCampoPlanta) {

    const isCientifico = titulo === "Nome científico:";

    return (
        <View style={styles.container}>
            <View style={styles.tipoInput}>
                <Text style={styles.estiloTitulo}>{titulo}</Text>
            </View>
            <View style = {styles.botaoinput}>
                <Text style={[styles.estiloConteudo, isCientifico && styles.estiloConteudoItalico]}> {conteudo} </Text>
            </View>
        </View>
    );
};

const styles = StyleSheet.create({
    container: {
        display: 'flex',
        width: width * 0.9,
        height: height * 0.085,
        marginTop: height * 0.03,
    },
    tipoInput: {
        height: '60%',
        marginLeft: width * 0.005,
    },
    botaoinput: {
        height: '60%',
    },
    estiloTitulo: {
        fontSize: 25,
        fontWeight: 'light',
    },
    estiloConteudo: {
        fontSize: 18,
        fontWeight: 'bold',
    },
    estiloConteudoItalico: {
        fontSize: 18,
        fontWeight: 'bold',
        fontStyle: 'italic',
    }
})