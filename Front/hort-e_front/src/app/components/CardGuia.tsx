import React from 'react';
import { View, Text, StyleSheet, Dimensions, TouchableOpacity, Image } from 'react-native';

const { height, width } = Dimensions.get('window');

type propsCardGuia = {
    title: string;
    ideais: string;
    image: string;
    onPress: () => void;
}

export default function CardGuia({title, ideais, image, onPress}: propsCardGuia) {
    return (
        <TouchableOpacity style={styles.container} onPress={onPress}>
            <View style={styles.divTextos}>
                <Text style={styles.text}>{ideais}</Text>
                <Text style={styles.text}>{title}</Text>
            </View>
            <View style={styles.imagemContainer}> 
                <Image
                    source={require('../assets/images/manjericao.png')}
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
        resizeMode: 'stretch',
    },
    text: {
        fontSize: 20,
        fontWeight: 'regular',
        color: '#000000',
    },
})