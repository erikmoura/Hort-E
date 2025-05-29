import React from "react";
import { View, StyleSheet, Dimensions, TouchableOpacity } from "react-native";
import { useRouter } from "expo-router";

const { height, width } = Dimensions.get("window");

export default function FotoPerfil(){

    return (

        <View style={styles.container}>
            <View style={styles.foto} />
            <TouchableOpacity style={styles.botaoEdit} />
        </View>


    );
};

const styles = StyleSheet.create({
    container: {
        height: height * 0.15,
        width: width * 0.35,
    },
    foto: {
        height: '100%',
        width: '100%',
        backgroundColor: '#D9D9D9',
        position: 'absolute',
        borderRadius: 12,
    },
    botaoEdit: {
        height: '40%',
        width: '40%',
        backgroundColor: '#257D3C',
        borderRadius: 12,
        position: 'absolute',
        bottom: (height * 0.02) * -1,
        right: (width * 0.05) * -1,
    },
});
