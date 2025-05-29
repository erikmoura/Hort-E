import React from "react";
import { View, StyleSheet, Dimensions, ScrollView } from "react-native";
import { useRouter } from "expo-router";
import BannerTopo from "../../components/BannerTopo";
import Post from "../../components/Post";

const { height, width } = Dimensions.get("window");

export default function Community() {

    return (
        <View style={styles.container}>
            <View style={styles.estiloBanner}>
                <BannerTopo title="Comunidade" />
            </View>
            <ScrollView style={styles.scrollview}>
                <Post />
                <Post />
                <Post />
                <View style={styles.blocoInvisivel}/>
            </ScrollView>
            
        </View>

    );
};

const styles = StyleSheet.create({
    container: {
        display: 'flex',
        flex: 1,
        justifyContent: 'center',
        alignItems: 'center',
        backgroundColor: '#F8F8F8'
    },
    estiloBanner: {
        position: 'absolute',
        top: 0,
        width: '100%',
    },
    scrollview: {
        marginTop: height * 0.12,
    },
    blocoInvisivel: {
        height: height * 0.12,
        width: '100%'
    }
})
