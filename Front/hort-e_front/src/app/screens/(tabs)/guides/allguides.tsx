import { Ionicons as Icon } from '@expo/vector-icons';
import { Picker } from '@react-native-picker/picker';
import { useFocusEffect } from '@react-navigation/native';
import { useRouter } from 'expo-router';
import React, { useCallback, useEffect, useState } from 'react';
import { Alert, Dimensions, Modal, ScrollView, StyleSheet, Text, TouchableOpacity, View } from 'react-native';
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

    const [modalVisible, setModalVisible] = useState(false);
    const [tipo, setTipo] = useState('todos');
    const opcoesTipo = ['todos', 'Interior', 'Exterior', 'Interior e Exterior'];

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

    const aplicarFiltro = async () => {
            try {
                const queryParams = new URLSearchParams();
                
                if (tipo === 'Interior') {
                    queryParams.append('guiaTipo', '0');
                } else if (tipo === 'Exterior') {
                    queryParams.append('guiaTipo', '1');
                } else if (tipo === 'Interior e Exterior') {
                    queryParams.append('guiaTipo', '2');
                }
    
                const url = `http://10.0.2.2:8080/api/guias/filtrar?${queryParams.toString()}`;
    
                const response = await fetch(url, {
                    method: 'GET',
                    headers: {
                        'Authorization': `Bearer ${token}`,
                        'Content-Type': 'application/json',
                    },
                });
    
                if (!response.ok) throw new Error('Erro ao filtrar plantas');
    
                const data = await response.json();
                setGuiasFiltrados(data);
                setModalVisible(false);
            } catch (error) {
                console.error(error);
                Alert.alert('Erro', 'Não foi possível aplicar o filtro.');
            }
        };
    
        const aplicarFiltroComValores = async (filtros) => {
            try {
                const queryParams = new URLSearchParams();
    
                if (filtros.tipo !== 'todos') queryParams.append('guiaTipo', filtros.tipo);
                
                const url = `http://10.0.2.2:8080/api/guias/filtrar?${queryParams.toString()}`;
    
                const response = await fetch(url, {
                    method: 'GET',
                    headers: {
                        'Authorization': `Bearer ${token}`,
                        'Content-Type': 'application/json',
                    },
                });
    
                if (!response.ok) throw new Error('Erro ao filtrar plantas');
    
                const data = await response.json();
                setGuiasFiltrados(data);
                setModalVisible(false);
            } catch (error) {
                console.error(error);
                Alert.alert('Erro', 'Não foi possível aplicar o filtro.');
            }
        };
    
    
        const resetarFiltros = () => {
            const filtrosResetados = {
                tipo: 'todos',
            };
    
            setTipo(filtrosResetados.tipo);
    
            aplicarFiltroComValores(filtrosResetados);
        };

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
                        <TouchableOpacity style={styles.iconeFiltro} onPress={() => setModalVisible(true)}>
                            <Icon
                                name="filter"
                                size={24}
                                color="#000000"
                                                
                            />
                        </TouchableOpacity>
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
            <Modal
                animationType="slide"
                transparent={true}
                visible={modalVisible}
                onRequestClose={() => setModalVisible(false)}
            >
                <View style={styles.background}>
                    <View style={styles.modalContainer}>
                        <View style={styles.modalContent}>
                            <View style={styles.botaoFechar}>
                                <TouchableOpacity onPress={() => setModalVisible(false)}>
                                    <Icon name="chevron-back" size={35} color="#000000" />
                                </TouchableOpacity>
                            </View>
                            <Text style={styles.titulosModal}>Tipo de Guia</Text>
                            <Picker selectedValue={tipo} onValueChange={setTipo}>
                                {opcoesTipo.map(opcao => (
                                    <Picker.Item key={opcao} label={opcao} value={opcao} />
                                ))}
                            </Picker>
                            <View style={styles.botaoAplicar}>
                                <TouchableOpacity style={styles.botoesFim} onPress={aplicarFiltro}>
                                    <Text style={{color: '#fff'}}>Aplicar</Text>
                                </TouchableOpacity>
                            </View>
                            <View style={styles.botaoResetar}>
                                <TouchableOpacity style={styles.botoesFim} onPress={resetarFiltros}>
                                    <Text style={{color: '#fff'}}>Resetar</Text>
                                </TouchableOpacity>
                            </View>
                            
                        </View>
                    </View>
                </View>
                
            </Modal>
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
    iconeFiltro: {
        position: "absolute",
        right: width * 0.05,
        top: height * 0.017,
    },
    background: {
        flex: 1,
        backgroundColor: 'rgba(0, 0, 0, 0.1)',
    },
    modalContainer: {
        position: 'absolute',
        top: height * 0.25,
        left: width * 0.05,
        height: '100%',
        width: '90%',
    },
    modalContent: {
        backgroundColor: 'white',
        borderRadius: 20,
        padding: height * 0.02,
        elevation: 5,
    },
    titulosModal: {
        fontSize: 18,
        fontWeight: 'bold'
    },
    botaoAplicar: {
        bottom: height * 0.001,
        left: width * 0.6,
        marginTop: height * 0.02,
    },
    botaoResetar: {
        position: 'absolute',
        bottom: height * 0.021,
        left: width * 0.4,
        marginTop: height * 0.02,
    },
    botoesFim: {
        backgroundColor: '#257D3C',
        borderRadius: 20,
        width: width * 0.2,
        height: height * 0.05,
        alignItems: 'center',
        justifyContent: 'center',
        elevation: 2,
        boxShadow: '0 4px 6px rgba(0, 0, 0, 0.1)',
    },
    botaoFechar: {
        right: width * 0.01,
        zIndex: 1,
        marginBottom: height * 0.02,
    },
});
