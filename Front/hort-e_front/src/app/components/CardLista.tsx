import React from 'react';
import { View, Text, StyleSheet, Dimensions, ImageBackground, TouchableOpacity } from 'react-native';


const { height, width } = Dimensions.get('window');

type CardListaProps = {
    title: string;
    onPress: () => void;
    image: string;
}

export default function CardLista({title, onPress, image}: CardListaProps) {
    return (
        <TouchableOpacity style={styles.container}>
            <ImageBackground 
                source={require('../assets/images/manjericao.png')} 
                style={styles.imagemContainer}
                imageStyle={styles.image}>
                <View style={styles.mascara} /> 
                <Text style={styles.title}>{title}</Text>
            </ImageBackground>
        </TouchableOpacity>
    );



};

const styles = StyleSheet.create({
    container:{
        width: width * 0.9,
        height: height * 0.08,
        borderRadius: 12,
        overflow: 'hidden',
        marginBottom: height * 0.015,
    },
    imagemContainer: {
        width: '100%',
        height: '100%',
        justifyContent: 'center',
    },
    image: {
        resizeMode: 'cover',
    },
    mascara: {
        width: '100%',
        height: '100%',
        backgroundColor: '#000000',
        opacity: 0.4,
    },
    title: {
        marginLeft: width * 0.08,
        fontSize: 25,
        fontWeight: 'bold',
        color: '#ffffff',
        position: 'absolute',
        zIndex: 1,
    }
})