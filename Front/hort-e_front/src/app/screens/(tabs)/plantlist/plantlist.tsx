import { useFocusEffect } from "@react-navigation/native";
import { useRouter } from "expo-router";
import React, { useCallback, useEffect, useState } from 'react';
import { Alert, Dimensions, ScrollView, StyleSheet, View } from "react-native";
import CardLista from "../../../components/CardLista";
import PesquisaPlantas from "../../../components/PesquisaPlantas";
import { useAuth } from '../../../hooks/useAuth';

const { height, width } = Dimensions.get("window");

export default function PlantList() {

    const router = useRouter();
    const { token } = useAuth();
    
    const [plantas, setPlantas] = useState([]);
    const [plantasFiltradas, setPlantasFiltradas] = useState([]);
    const [textoPesquisa, setTextoPesquisa] = useState('');
    
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

    return (
        <View style={styles.container}>
            <View style = {styles.botaoinput}>
                <PesquisaPlantas
                    label="Pesquisar..."
                    value={textoPesquisa}
                    onChangeText={setTextoPesquisa}
                />
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
