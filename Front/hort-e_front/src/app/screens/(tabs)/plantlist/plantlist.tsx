import React from "react";
import { View, StyleSheet, Dimensions, ScrollView } from "react-native";
import { useRouter } from "expo-router";
import CardLista from "../../../components/CardLista";
import BotaoInput from "../../../components/BotaoInput";

const { height, width } = Dimensions.get("window");

export default function PlantList() {

    return (
        <View style={styles.container}>
            <View style = {styles.botaoinput}>
                <BotaoInput
                    label="Pesquisar..."
                    onPress={() => {}}
                />
            </View>
            <ScrollView style={styles.scrollview}>
                <CardLista
                    title="Manjericão"
                    onPress={() => {}}
                    image="https://s2.glbimg.com/XsEfbtMEWzj4dv-twPko6BAmS0M=/512x320/smart/e.glbimg.com/og/ed/f/original/2017/04/07/thinkstockphotos-92693911.jpg"
                />
                <CardLista
                    title="Manjericão"
                    onPress={() => {}}
                    image="https://s2.glbimg.com/XsEfbtMEWzj4dv-twPko6BAmS0M=/512x320/smart/e.glbimg.com/og/ed/f/original/2017/04/07/thinkstockphotos-92693911.jpg"
                />
                <CardLista
                    title="Manjericão"
                    onPress={() => {}}
                    image="https://s2.glbimg.com/XsEfbtMEWzj4dv-twPko6BAmS0M=/512x320/smart/e.glbimg.com/og/ed/f/original/2017/04/07/thinkstockphotos-92693911.jpg"
                />
                <CardLista
                    title="Manjericão"
                    onPress={() => {}}
                    image="https://s2.glbimg.com/XsEfbtMEWzj4dv-twPko6BAmS0M=/512x320/smart/e.glbimg.com/og/ed/f/original/2017/04/07/thinkstockphotos-92693911.jpg"
                />
                <CardLista
                    title="Manjericão"
                    onPress={() => {}}
                    image="https://s2.glbimg.com/XsEfbtMEWzj4dv-twPko6BAmS0M=/512x320/smart/e.glbimg.com/og/ed/f/original/2017/04/07/thinkstockphotos-92693911.jpg"
                />
                <CardLista
                    title="Manjericão"
                    onPress={() => {}}
                    image="https://s2.glbimg.com/XsEfbtMEWzj4dv-twPko6BAmS0M=/512x320/smart/e.glbimg.com/og/ed/f/original/2017/04/07/thinkstockphotos-92693911.jpg"
                />
                <CardLista
                    title="Manjericão"
                    onPress={() => {}}
                    image="https://s2.glbimg.com/XsEfbtMEWzj4dv-twPko6BAmS0M=/512x320/smart/e.glbimg.com/og/ed/f/original/2017/04/07/thinkstockphotos-92693911.jpg"
                />
                <CardLista
                    title="Manjericão"
                    onPress={() => {}}
                    image="https://s2.glbimg.com/XsEfbtMEWzj4dv-twPko6BAmS0M=/512x320/smart/e.glbimg.com/og/ed/f/original/2017/04/07/thinkstockphotos-92693911.jpg"
                />
                <CardLista
                    title="Manjericão"
                    onPress={() => {}}
                    image="https://s2.glbimg.com/XsEfbtMEWzj4dv-twPko6BAmS0M=/512x320/smart/e.glbimg.com/og/ed/f/original/2017/04/07/thinkstockphotos-92693911.jpg"
                />
                <CardLista
                    title="Manjericão"
                    onPress={() => {}}
                    image="https://s2.glbimg.com/XsEfbtMEWzj4dv-twPko6BAmS0M=/512x320/smart/e.glbimg.com/og/ed/f/original/2017/04/07/thinkstockphotos-92693911.jpg"
                />
                <CardLista
                    title="Manjericão"
                    onPress={() => {}}
                    image="https://s2.glbimg.com/XsEfbtMEWzj4dv-twPko6BAmS0M=/512x320/smart/e.glbimg.com/og/ed/f/original/2017/04/07/thinkstockphotos-92693911.jpg"
                />
                <CardLista
                    title="Manjericão"
                    onPress={() => {}}
                    image="https://s2.glbimg.com/XsEfbtMEWzj4dv-twPko6BAmS0M=/512x320/smart/e.glbimg.com/og/ed/f/original/2017/04/07/thinkstockphotos-92693911.jpg"
                />
                <View style={styles.blocoInvisivel}/>
            </ScrollView>
        </View>
        

    );
};

const styles = StyleSheet.create({
    container: {
        flex: 1,
        alignItems: "center",
        justifyContent: "center",
        backgroundColor: "#F8F8F8",
    },
    scrollview: {
        marginTop: height * 0.14,
    },
    botaoinput: {
        position: "absolute",
        width: "100%",
        top: height * 0.05,
    },
    blocoInvisivel: {
        height: height * 0.12,
        width: '100%'
    }
})
