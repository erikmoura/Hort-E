import React from 'react';
import { Dimensions, Image, StyleSheet, Text, TouchableOpacity, View } from 'react-native';

const { height, width } = Dimensions.get('window');

type propsCardGuia = {
    title: string;
    ideais: string;
    image: string;
    onPress: () => void;
}

export default function CardGuia({title, ideais, image, onPress}: propsCardGuia) {
    
    let displayValue = ideais;

    if (ideais.toString() === '0') {
        displayValue = 'Interior';
    } else if (ideais.toString() === '1') {
        displayValue = 'Exterior';
    } else if (ideais.toString() === '2') {
        displayValue = 'Interior e Exterior';
    }
    
    return (
        <TouchableOpacity style={styles.container} onPress={onPress}>
            <View style={styles.divTextos}>
                <Text style={styles.text}>Ideal para: {displayValue}</Text>
                <Text style={styles.text}>{title}</Text>
            </View>
            <View style={styles.imagemContainer}> 
                <Image
                    source={{ uri: image }}
                    style={styles.image}
                />
            </View>
        </TouchableOpacity>
    );
};

const styles = StyleSheet.create({
    container: {
        width: width * 0.9,
        height: height * 0.248,
        backgroundColor: '#FFFFFF',
        borderRadius: 12,
        marginBottom: height * 0.02,
        overflow: 'hidden',
        boxShadow: '0px 6px 6px rgba(0, 0, 0, 0.1)',
    },
    divTextos: {
        display: 'flex',
        flexDirection: 'column',
        height: '30%',
        paddingLeft: width * 0.04,
        justifyContent: 'center',

    },
    imagemContainer: {
        width: '100%',
        height: '70%',
    },
    image: {
        width: '100%',
        height: '100%',
        resizeMode: 'cover',
    },
    text: {
        fontSize: 20,
        fontWeight: 'regular',
        color: '#000000',
    },
})