import React from 'react';
import { Dimensions, StyleSheet, Text, View } from 'react-native';
import BotaoInput from '../components/BotaoInput';

const { height, width } = Dimensions.get('window');

type propsInputPerfil = {
    titulo: string;
    label: string;
    value: string;
    onChangeText: (text: string) => void;
}

export default function InputPerfil({titulo, label, value, onChangeText}: propsInputPerfil) {
    
    let displayValue = value;

    if (value.toString() === '0') {
        displayValue = 'Interior';
    } else if (value.toString() === '1') {
        displayValue = 'Exterior';
    } else if (value.toString() === '2') {
        displayValue = 'Interior e Exterior';
    } else {
        displayValue = value;
    }
    
    return (
        <View style={styles.container}>
            <View style={styles.tipoInput}>
                <Text style={styles.estiloTipo}>{titulo}</Text>
            </View>
            <View style = {styles.botaoinput}>
                <BotaoInput
                    label={label}
                    value={displayValue}
                    onChangeText={onChangeText}
                />
            </View>
        </View>
    );
};

const styles = StyleSheet.create({
    container: {
        display: 'flex',
        flexDirection: 'column',
        width: width * 0.9,
        height: height * 0.1,
        marginBottom: height * 0.02,
    },
    tipoInput: {
        height: '30%',
        marginBottom: height * 0.001,
    },
    botaoinput: {
        height: '70%',
        justifyContent: 'center',
        alignItems: 'center',
    },
    estiloTipo: {
        fontSize: 16,
        fontWeight: 'regular',
    }
})