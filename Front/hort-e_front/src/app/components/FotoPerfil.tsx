import { MaterialCommunityIcons as Icon } from "@expo/vector-icons";
import React from "react";
import { Dimensions, Image, StyleSheet, TouchableOpacity, View } from "react-native";


const { height, width } = Dimensions.get("window");

type propsFotoPerfil = {
    value: string;
}

export default function FotoPerfil({ value }: propsFotoPerfil) {
    return (
        <View style={styles.container}>
            <View style={styles.foto}>
                <Image
                    source={{ uri: value }}
                    style={styles.image}
                />
            </View>
            <TouchableOpacity style={styles.botaoEdit}>
                <Icon name="pencil-outline" size={30} color="#FFFFFF" />
            </TouchableOpacity>
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
        overflow: 'hidden',
        position: 'absolute',
        borderRadius: 12,
    },
    image: {
        height: '100%',
        width: '100%',
        resizeMode: 'cover',
    },
    botaoEdit: {
        height: '40%',
        width: '40%',
        backgroundColor: '#257D3C',
        borderRadius: 12,
        position: 'absolute',
        bottom: (height * 0.02) * -1,
        right: (width * 0.05) * -1,
        justifyContent: 'center',
        alignItems: 'center',
    },
});
