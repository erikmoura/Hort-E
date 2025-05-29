import React from 'react';
import { View, StyleSheet, Dimensions, ScrollView } from 'react-native';

const {height, width} = Dimensions.get('window')

type propsPost = {



}

export default function Post(){

    return (
        <View style={styles.container}>
            <View style={styles.superior} />
            <View style={styles.imagem} />
            <View style={styles.inferior} />
        </View>

    );

}

const styles = StyleSheet.create({
    container:{
        width: width * 1,
        height: height * 0.3,
        alignItems: 'center',
        justifyContent: 'center',
        marginBottom: height * 0.01,
    },
    superior:{
        width: '100%',
        height: '20%',
        backgroundColor: '#FFFFFF',
        position: 'absolute',
        top: 0,
    },
    inferior:{
        width: '100%',
        height: '20%',
        backgroundColor: '#FFFFFF',
        position: 'absolute',
        bottom: 0,
    },
    imagem: {
        width: '100%',
        height: '60%',
        backgroundColor: '#000000'
    }
})