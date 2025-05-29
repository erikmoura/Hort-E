import React from 'react';
import { View, Text, StyleSheet, Dimensions, ScrollView } from 'react-native'; 
import { useRouter } from 'expo-router';
import BotaoVerde from '../../../components/BotaoVerde';
import BannerTopo from '../../../components/BannerTopo';
import CardGuia from '../../../components/CardGuia';

const { height, width } = Dimensions.get('window');

export default function AllGuides() {
    const router = useRouter();
    
    return (
        <View style = {styles.container}>
            <View style={styles.bannerTopo}>
                <BannerTopo title="Guias de cultivo" />
            </View>
            <ScrollView style={styles.scrollview}>
                <CardGuia
                    title="Sugestões para cuidar no inverno."
                    ideais="Ideal para: Apartamento"
                    onPress={() => router.push('../(tabs)/guides/guide1')}
                    image="https://s2.glbimg.com/XsEfbtMEWzj4dv-twPko6BAmS0M=/512x320/smart/e.glbimg.com/og/ed/f/original/2017/04/07/thinkstockphotos-92693911.jpg"
                />
                <CardGuia
                    title="Guia de cultivo: manjericão."
                    ideais="Ideal para: Apartamento"
                    onPress={() => router.push('../(tabs)/guides/guide1')}
                    image="https://s2.glbimg.com/XsEfbtMEWzj4dv-twPko6BAmS0M=/512x320/smart/e.glbimg.com/og/ed/f/original/2017/04/07/thinkstockphotos-92693911.jpg"
                />
                <CardGuia
                    title="Sugestões para cuidar no inverno."
                    ideais="Ideal para: Apartamento"
                    onPress={() => router.push('../(tabs)/guides/guide1')}
                    image="https://s2.glbimg.com/XsEfbtMEWzj4dv-twPko6BAmS0M=/512x320/smart/e.glbimg.com/og/ed/f/original/2017/04/07/thinkstockphotos-92693911.jpg"
                />
                <CardGuia
                    title="Sugestões para cuidar no inverno."
                    ideais="Ideal para: Apartamento"
                    onPress={() => router.push('../(tabs)/guides/guide1')}
                    image="https://s2.glbimg.com/XsEfbtMEWzj4dv-twPko6BAmS0M=/512x320/smart/e.glbimg.com/og/ed/f/original/2017/04/07/thinkstockphotos-92693911.jpg"
                />
                <View style={styles.blocoInvisivel}/>
            </ScrollView>
            <View style={styles.botaoPesquisar}>
                <BotaoVerde label="Pesquisar" onPress={() => []}/>
            </View>
        </View>
    );
};

const styles = StyleSheet.create({
    container: {
        display: 'flex',
        justifyContent: 'center',
        alignItems: 'center',
        flex: 1,
        backgroundColor: '#F8F8F8',
    },
    bannerTopo: {
        position: 'absolute',
        width: '100%',
        top: 0,
    },
    scrollview: {
        marginTop: height * 0.14,
    },
    botaoPesquisar: {
        position: 'absolute',
        bottom: height * 0.135,
        elevation: 5,
        boxShadow: '0 4px 6px rgba(0, 0, 0, 0.1)',
    },
    blocoInvisivel: {
        height: height * 0.22,
        width: '100%'
    }
})
