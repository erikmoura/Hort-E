import { Ionicons as Icon } from '@expo/vector-icons';
import { useLocalSearchParams, useRouter } from "expo-router";
import React from 'react';
import { Dimensions, Image, ScrollView, StyleSheet, Text, TouchableOpacity, View } from "react-native";

const { height, width } = Dimensions.get("window");

export default function IsolatedGuide() {
    const router = useRouter();
    const { id, title, ideais, image, content, plants } = useLocalSearchParams();

    const tituloFormatado = title.replace(" para", "\npara");

    return (
        <View style={styles.container}>
            <ScrollView >
                <View style={styles.botaoVoltar}>
                    <TouchableOpacity onPress={() => router.back()}>
                        <Icon name="chevron-back" size={50} color="#fff" />
                    </TouchableOpacity>
                </View>
                <View style={styles.imageContainer}>
                    <Image
                        source={{uri: image}}
                        style={styles.image}
                    />
                </View>
                <Text style={styles.styleTitle}>
                    {tituloFormatado}
                </Text>
                <Text style={styles.styleContent}>
                    {content}
                </Text>
                <View style={styles.blocoInvisivel} />
            </ScrollView>
        </View>
    )
}

const styles = StyleSheet.create({
    container: {
        flex: 1,
        alignItems: "center",
        justifyContent: "center",
        backgroundColor: "#F8F8F8",
    },
    imageContainer: {
        width: "100%",
        height: height * 0.35,
        overflow: "hidden",
    },
    image:{
        width: "100%",
        height: "100%",
        resizeMode: "cover",
    },
    styleTitle: {
        fontSize: 40,
        fontWeight: "bold",
        color: "#000000",
        marginVertical: height * 0.02,
        marginHorizontal: width * 0.05,
    },
    styleContent: {
        fontSize: 16,
        fontWeight: "regular",
        color: "#000000",
        marginHorizontal: width * 0.05,
    },
    blocoInvisivel: {
        height: height * 0.12,
        width: '100%',
    },
    botaoVoltar: {
        position: "absolute",
        top: height * 0.06,
        left: width * 0.03,
        zIndex: 1,
    }
});