import { Ionicons as Icon } from '@expo/vector-icons';
import { useFocusEffect } from '@react-navigation/native';
import { useRouter } from 'expo-router';
import React, { useCallback, useEffect, useState } from 'react';
import { Alert, Dimensions, ScrollView, StyleSheet, TouchableOpacity, View } from 'react-native';
import BannerTopo from '../../../components/BannerTopo';
import BotaoVerde from '../../../components/BotaoVerde';
import CardGuia from '../../../components/CardGuia';
import PesquisaGuias from '../../../components/PesquisaGuias';
import { useAuth } from '../../../hooks/useAuth';

const { height, width } = Dimensions.get('window');

export default function AllGuides() {
    const router = useRouter();
    const { token } = useAuth();

    const [guias, setGuias] = useState([]);
    const [guiasFiltrados, setGuiasFiltrados] = useState([]);
    const [modoPesquisa, setModoPesquisa] = useState(false);
    const [textoPesquisa, setTextoPesquisa] = useState('');

    useFocusEffect(
        useCallback(() => {
            if (!token) return;

            const fetchGuias = async () => {
                try {
                    const response = await fetch('http://10.0.2.2:8080/api/guias', {
                        method: 'GET',
                        headers: {
                            'Authorization': `Bearer ${token}`,
                            'Content-Type': 'application/json',
                        },
                    });

                    if (!response.ok) {
                        throw new Error('Erro ao buscar guias');
                    }

                    const data = await response.json();
                    setGuias(data);
                    setGuiasFiltrados(data);
                } catch (error) {
                    console.error(error);
                    Alert.alert('Erro', 'Não foi possível carregar os guias.');
                }
            };

            fetchGuias();
        }, [token])
    );

    useEffect(() => {
        const texto = textoPesquisa.toLowerCase();
        const filtrados = guias.filter((guia) =>
            guia.guiaTitulo.toLowerCase().includes(texto)
        );
        setGuiasFiltrados(filtrados);
    }, [textoPesquisa, guias]);

    return (
        <View style={styles.container}>
            <View style={styles.bannerTopo}>
                {modoPesquisa ? (
                <View style={styles.pesquisaAtiva}>
                    <View style={styles.volta}>
                        <TouchableOpacity
                            onPress={() => {
                                setModoPesquisa(false);
                                setTextoPesquisa('');
                                setGuiasFiltrados(guias);
                            }}
                        >
                            <Icon name="chevron-back" size={48} color="#000" style={{opacity: 0.7}} />
                        </TouchableOpacity>
                    </View>
                    <View style={styles.pesquisa}>
                        <PesquisaGuias
                            label= "Pesquisar..."
                            value={textoPesquisa}
                            onChangeText={setTextoPesquisa}
                        />
                    </View>
                </View>
                ) : (
                    <BannerTopo title="Guias de cultivo" />
                )}
            </View>
                <ScrollView style={styles.scrollview}>
                    {guiasFiltrados.map((guia) => (
                        <CardGuia
                            key={guia.id}
                            title={guia.guiaTitulo}
                            ideais={guia.guiaTipo}
                            image={guia.guiaImagemUrl}
                            onPress={() => 
                                router.push({
                                    pathname: '/screens/(isolated)/isolatedguide',
                                    params: {
                                        id: guia.id,
                                        title: guia.guiaTitulo,
                                        ideais: guia.guiaTipo,
                                        image: guia.guiaImagemUrl,
                                        content: guia.guiaConteudo,
                                        plants: guia.plantasAssociadasIds,
                                    },
                                })
                            }
                        />
                    ))}
                    <View style={styles.blocoInvisivel} />
                </ScrollView>

            {!modoPesquisa && (
                <View style={styles.botaoPesquisar}>
                <BotaoVerde label="Pesquisar" onPress={() => setModoPesquisa(true)} />
            </View>
            )}
        </View>
    );
}

const styles = StyleSheet.create({
    container: {
        display: 'flex',
        justifyContent: 'center',
        alignItems: 'center',
        flex: 1,
        backgroundColor: '#F8F8F8',
    },
    pesquisaAtiva:{
        position: 'absolute',
        flexDirection: 'row',
        alignItems: 'center',
        top: height * 0.09,
        width: '100%',
    },
    volta:{
        position: 'absolute',
        left: width * 0.015,
        zIndex: 1,
    },
    pesquisa:{
        position: 'absolute',
        left: width * 0.15,
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
        width: '100%',
    },
});
