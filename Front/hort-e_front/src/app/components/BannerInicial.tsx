import React from 'react';
import { Text, StyleSheet, ImageBackground, Dimensions } from 'react-native';

const { height, width } = Dimensions.get('window');

export default function BannerInicial() {
    return (
        <ImageBackground
            source={require('../assets/images/imagemLanding.png')}
            style={styles.imageBackground}
            imageStyle={styles.image}
            >
            <Text style={styles.logo}>(plantinha) Hort-E</Text>
            <Text style={styles.title}>
                Sua solução{'\n'}
                para{'\n'}
                hortas{'\n'}
                sustentáveis.
            </Text>
        </ImageBackground>
        
        
            
        
    );
};

const styles = StyleSheet.create({
    imageBackground: {
        width: '100%',
        height: '90%',
        position: 'absolute',
        top: 0,
    },
    image: {
        width: '100%',
        borderBottomLeftRadius: 70,
        borderBottomRightRadius: 70,
        elevation: 5,
        boxShadow: '0 4px 6px rgba(0, 0, 0, 0.1)',
        resizeMode: 'cover',
    },
    logo: {
        fontSize: 42,
        fontWeight: 'black',
        color: '#fff',
        position: 'absolute',
        top: height * 0.045,
        left: width * 0.06,
        elevation: 6,
    },
    title: {
        fontSize: 59,
        fontWeight: 'bold',
        color: '#fff',
        lineHeight: 67,
        position: 'absolute',
        top: height * 0.293,
        left: width * 0.06,
    },
});