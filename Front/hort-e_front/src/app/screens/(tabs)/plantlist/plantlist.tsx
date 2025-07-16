import { Ionicons as Icon } from '@expo/vector-icons';
import { useFocusEffect } from "@react-navigation/native";
import { useRouter } from "expo-router";
import React, { useCallback, useEffect, useState } from 'react';
import { Alert, Dimensions, Modal, ScrollView, StyleSheet, Text, TouchableOpacity, View } from "react-native";
import CardLista from "../../../components/CardLista";
import PesquisaPlantas from "../../../components/PesquisaPlantas";
import { useAuth } from '../../../hooks/useAuth';
 
import { Picker } from '@react-native-picker/picker';


const { height, width } = Dimensions.get("window");

export default function PlantList() {

    const router = useRouter();
    const { token } = useAuth();
    
    const [plantas, setPlantas] = useState([]);
    const [plantasFiltradas, setPlantasFiltradas] = useState([]);
    const [textoPesquisa, setTextoPesquisa] = useState('');

    const [modalVisible, setModalVisible] = useState(false);

    const [categoria, setCategoria] = useState('todos');
    const [tipoSolo, setTipoSolo] = useState('todos');
    const [irrigacao, setIrrigacao] = useState('todos');
    const [localPlantio, setLocalPlantio] = useState('todos');
    const [clima, setClima] = useState('todos');
    const [luzSolar, setLuzSolar] = useState('todos');

    const opcoesCategoria = ['todos', 'Erva', 'Hortaliça', 'Fruto', 'Raiz', 'Bulbo', 'Tubérculo', 'Leguminosa'];
    const opcoesTipoSolo = ['todos', 'Fértil e Bem Drenado', 'Rico em Matéria Orgânica (Húmus)', 'Leve e Bem Drenado', 'Arenoso e Bem Drenado (Pouco Fértil / Seco)', 'Rico e Profundo', 'Rico e Levemente Ácido', 'Rico em Matéria Orgânica e Bem Drenado', 'Bem Drenado (Até Pobre)'];
    const opcoesIrrigacao = ['todos', 'diária', 'a cada 1-2 dias', 'a cada 2-3 dias', 'a cada 3-5 dias', 'semanal', 'a cada 7-15 dias'];
    const opcoesLocalPlantio = ['todos', 'Apartamentos e Casas (Sol Pleno)', 'Todos os Locais (Ideal em Vasos)', 'Varandas e Hortas Elevadas', 'Janelas, Varandas, Vasos ou Jardineiras', 'Vasos Grandes e Quintais/Jardins', 'Vasos (Local Ensolarado)', 'Varandas e Quintais Ensolarados', 'Varandas e Jardineiras', 'Vasos e Canteiros (Sombreados/Luz Difusa)', 'Vasos e Jardineiras', 'Quintais e Hortas Amplas', 'Varanda, Dentro (Perto de Janela) e Quintal'];
    const opcoesClima = ['todos', 'Mediterrâneo', 'Temperado a Subtropical', 'Temperado', 'Tropical a Subtropical', 'Temperado a Quente', 'Tropical', 'Temperado a Frio', 'Tropical a Temperado', 'Tropical a Quente'];
    const opcoesLuzSolar = ['todos', 'Sol pleno (4-6h)', 'Meia-sombra', 'Sol pleno ou meia-sombra', 'Sol pleno (6-8h)', 'Sol pleno (6-7h)', 'Sol pleno (4h+) (tolera um pouco de sombra)'];
    
    useFocusEffect(
        useCallback(() => {
            if (!token) return;
    
            const fetchPlantas = async () => {
                try {
                    const response = await fetch('http://10.0.2.2:8080/api/plantas', {
                        method: 'GET',
                        headers: {
                            'Authorization': `Bearer ${token}`,
                            'Content-Type': 'application/json',
                        },
                    });
    
                    if (!response.ok) {
                        throw new Error('Erro ao buscar plantas');
                    }
    
                    const data = await response.json();
                    setPlantas(data);
                    setPlantasFiltradas(data);
                } catch (error) {
                    console.error(error);
                    Alert.alert('Erro', 'Não foi possível carregar as plantas.');
                }
            };
    
            fetchPlantas();
        }, [token]) // dispara novamente se o token mudar
    );

    useEffect(() => {
        const texto = textoPesquisa.toLowerCase();
        const filtradas = plantas.filter((planta) =>
            (planta.nomeComum ?? '').toLowerCase().includes(texto)
        );
        setPlantasFiltradas(filtradas);
    }, [textoPesquisa, plantas]);


    const aplicarFiltro = async () => {
        try {
            const queryParams = new URLSearchParams();

            if (categoria !== 'todos') queryParams.append('categoria', categoria);
            if (tipoSolo !== 'todos') queryParams.append('tipoSolo', tipoSolo);
            if (irrigacao !== 'todos') queryParams.append('irrigacao', irrigacao);
            if (localPlantio !== 'todos') queryParams.append('localPlantio', localPlantio);
            if (clima !== 'todos') queryParams.append('clima', clima);
            if (luzSolar !== 'todos') queryParams.append('luzSolar', luzSolar);

            const url = `http://10.0.2.2:8080/api/plantas/filtrar?${queryParams.toString()}`;

            const response = await fetch(url, {
                method: 'GET',
                headers: {
                    'Authorization': `Bearer ${token}`,
                    'Content-Type': 'application/json',
                },
            });

            if (!response.ok) throw new Error('Erro ao filtrar plantas');

            const data = await response.json();
            setPlantasFiltradas(data);
            setModalVisible(false);
        } catch (error) {
            console.error(error);
            Alert.alert('Erro', 'Não foi possível aplicar o filtro.');
        }
    };

    const aplicarFiltroComValores = async (filtros) => {
        try {
            const queryParams = new URLSearchParams();

            if (filtros.categoria !== 'todos') queryParams.append('categoria', filtros.categoria);
            if (filtros.tipoSolo !== 'todos') queryParams.append('tipoSolo', filtros.tipoSolo);
            if (filtros.irrigacao !== 'todos') queryParams.append('irrigacao', filtros.irrigacao);
            if (filtros.localPlantio !== 'todos') queryParams.append('localPlantio', filtros.localPlantio);
            if (filtros.clima !== 'todos') queryParams.append('clima', filtros.clima);
            if (filtros.luzSolar !== 'todos') queryParams.append('luzSolar', filtros.luzSolar);

            const url = `http://10.0.2.2:8080/api/plantas/filtrar?${queryParams.toString()}`;

            const response = await fetch(url, {
                method: 'GET',
                headers: {
                    'Authorization': `Bearer ${token}`,
                    'Content-Type': 'application/json',
                },
            });

            if (!response.ok) throw new Error('Erro ao filtrar plantas');

            const data = await response.json();
            setPlantasFiltradas(data);
            setModalVisible(false);
        } catch (error) {
            console.error(error);
            Alert.alert('Erro', 'Não foi possível aplicar o filtro.');
        }
    };


    const resetarFiltros = () => {
        const filtrosResetados = {
            categoria: 'todos',
            tipoSolo: 'todos',
            irrigacao: 'todos',
            localPlantio: 'todos',
            clima: 'todos',
            luzSolar: 'todos',
        };

        setCategoria(filtrosResetados.categoria);
        setTipoSolo(filtrosResetados.tipoSolo);
        setIrrigacao(filtrosResetados.irrigacao);
        setLocalPlantio(filtrosResetados.localPlantio);
        setClima(filtrosResetados.clima);
        setLuzSolar(filtrosResetados.luzSolar);

        aplicarFiltroComValores(filtrosResetados);
    };



    return (
        <View style={styles.container}>
            <View style = {styles.botaoinput}>
                <PesquisaPlantas
                    label="Pesquisar..."
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

            <ScrollView style={styles.scrollview}>
                {plantasFiltradas.map((planta) => (
                    <CardLista
                        key={planta.id}
                        title={planta.nomeComum}
                        image={planta.plantaImagemUrl}
                        onPress={() => 
                            router.push({
                                pathname: '/screens/(isolated)/plantinfo',
                                params: {
                                    id: planta.id,
                                    nome: planta.nomeComum,
                                    nomeCien: planta.nomeCientifico,
                                    categoria: planta.categoria,
                                    image: planta.plantaImagemUrl,
                                    descricao: planta.descricao,
                                    tipoSolo: planta.tipoSolo,
                                    irrigacao: planta.irrigacao,
                                    localPlantio: planta.localPlantio,
                                    clima: planta.clima,
                                    luzSolar: planta.luzSolar,
                                    guias: planta.guiasAssociadosIds,
                                },
                            })
                        }
                    />
                ))}
                <View style={styles.blocoInvisivel}/>
            </ScrollView>
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
                            <Text style={styles.titulosModal}>Categoria</Text>
                            <Picker selectedValue={categoria} onValueChange={setCategoria}>
                                {opcoesCategoria.map(opcao => (
                                    <Picker.Item key={opcao} label={opcao} value={opcao} />
                                ))}
                            </Picker>

                            <Text style={styles.titulosModal}>Tipo de Solo</Text>
                            <Picker selectedValue={tipoSolo} onValueChange={setTipoSolo}>
                                {opcoesTipoSolo.map(opcao => (
                                    <Picker.Item key={opcao} label={opcao} value={opcao} />
                                ))}
                            </Picker>

                            <Text style={styles.titulosModal}>Irrigação</Text>
                            <Picker selectedValue={irrigacao} onValueChange={setIrrigacao}>
                                {opcoesIrrigacao.map(opcao => (
                                    <Picker.Item key={opcao} label={opcao} value={opcao} />
                                ))}
                            </Picker>

                            <Text style={styles.titulosModal}>Local de Plantio</Text>
                            <Picker selectedValue={localPlantio} onValueChange={setLocalPlantio}>
                                {opcoesLocalPlantio.map(opcao => (
                                    <Picker.Item key={opcao} label={opcao} value={opcao} />
                                ))}
                            </Picker>

                            <Text style={styles.titulosModal}>Clima</Text>
                            <Picker selectedValue={clima} onValueChange={setClima}>
                                {opcoesClima.map(opcao => (
                                    <Picker.Item key={opcao} label={opcao} value={opcao} />
                                ))}
                            </Picker>

                            <Text style={styles.titulosModal}>Luz Solar</Text>
                            <Picker selectedValue={luzSolar} onValueChange={setLuzSolar}>
                                {opcoesLuzSolar.map(opcao => (
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
    iconeFiltro: {
        position: "absolute",
        right: width * 0.085,
        top: height * 0.02,
    },
    blocoInvisivel: {
        height: height * 0.12,
        width: '100%'
    },
    background: {
        flex: 1,
        backgroundColor: 'rgba(0, 0, 0, 0.1)',
    },
    modalContainer: {
        position: 'absolute',
        top: height * 0.1,
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
    }

})
