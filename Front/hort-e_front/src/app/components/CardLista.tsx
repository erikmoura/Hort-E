import { Ionicons as Icon } from '@expo/vector-icons';
import React from 'react';
import { Dimensions, ImageBackground, StyleSheet, Text, TouchableOpacity, View } from 'react-native';

const { height, width } = Dimensions.get('window');

type CardListaProps = {
    title: string;
    onPress: () => void;
    image: string;
}

export default function CardLista({title, onPress, image}: CardListaProps) {
    return (
        <TouchableOpacity style={styles.container} onPress = {onPress}>
            <ImageBackground 
                source={{ uri: image }}
                style={styles.imagemContainer}
                imageStyle={styles.image}>
                <View style={styles.mascara} /> 
                <Text style={styles.title}>{title}</Text>
                <Icon name="chevron-forward" size={34} color="#ffffff" style={styles.icone} />
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
        boxShadow: '0px 6px 6px rgba(0, 0, 0, 0.1)',
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
    },
    icone:{
        position: 'absolute',
        right: width * 0.05,
        top: height * 0.021,
    },
})