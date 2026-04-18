import React, { Component } from 'react';
import { MapTo } from '@adobe/aem-react-editable-components';

const PokemonEditConfig = {
    emptyLabel: 'Pokemon Card — configure o nome do Pokémon',
    isEmpty: (props) => !props.pokemonName,
};

const StatBar = ({ name, value }) => {
    const pct = Math.min((value / 255) * 100, 100);
    return (
        <div className="cmp-pokemon__stat">
            <span className="cmp-pokemon__stat-name">{name.replace('-', ' ')}</span>
            <span className="cmp-pokemon__stat-value">{value}</span>
            <div className="cmp-pokemon__stat-bar-wrap">
                <div className="cmp-pokemon__stat-bar" style={{ width: pct + '%' }} />
            </div>
        </div>
    );
};

class Pokemon extends Component {
    render() {
        const {
            pokemonName,
            pokemonId,
            displayName,
            imageUrl,
            height,
            weight,
            types = [],
            abilities = [],
            stats = {},
            found,
        } = this.props;

        if (!pokemonName) {
            return (
                <div className="cmp-pokemon">
                    <div className="cmp-pokemon__empty">
                        Configure o nome do Pokémon no dialog do AEM.
                    </div>
                </div>
            );
        }

        if (!found) {
            return (
                <div className="cmp-pokemon">
                    <div className="cmp-pokemon__card">
                        <div className="cmp-pokemon__not-found">
                            Pokémon <strong>{pokemonName}</strong> não encontrado.
                        </div>
                    </div>
                </div>
            );
        }

        const heightM = (height / 10).toFixed(1);
        const weightKg = (weight / 10).toFixed(1);

        return (
            <div className="cmp-pokemon">
                <div className="cmp-pokemon__card">

                    <div className="cmp-pokemon__header">
                        <span className="cmp-pokemon__id">#{String(pokemonId).padStart(3, '0')}</span>

                        <div className="cmp-pokemon__image-wrap">
                            {imageUrl && (
                                <img
                                    src={imageUrl}
                                    alt={displayName}
                                    className="cmp-pokemon__image"
                                />
                            )}
                        </div>

                        <h2 className="cmp-pokemon__name">{displayName}</h2>

                        <div className="cmp-pokemon__types">
                            {types.map(type => (
                                <span
                                    key={type}
                                    className={`cmp-pokemon__type cmp-pokemon__type--${type}`}
                                >
                                    {type}
                                </span>
                            ))}
                        </div>
                    </div>

                    <div className="cmp-pokemon__body">
                        <div className="cmp-pokemon__meta">
                            <div className="cmp-pokemon__meta-item">
                                <span className="cmp-pokemon__meta-label">Altura</span>
                                <span className="cmp-pokemon__meta-value">{heightM} m</span>
                            </div>
                            <div className="cmp-pokemon__meta-item">
                                <span className="cmp-pokemon__meta-label">Peso</span>
                                <span className="cmp-pokemon__meta-value">{weightKg} kg</span>
                            </div>
                        </div>

                        <p className="cmp-pokemon__section-title">Base Stats</p>
                        <div className="cmp-pokemon__stats">
                            {Object.entries(stats).map(([name, value]) => (
                                <StatBar key={name} name={name} value={value} />
                            ))}
                        </div>

                        {abilities.length > 0 && (
                            <>
                                <p className="cmp-pokemon__section-title">Habilidades</p>
                                <div className="cmp-pokemon__abilities">
                                    {abilities.map(ability => (
                                        <span key={ability} className="cmp-pokemon__ability">
                                            {ability.replace('-', ' ')}
                                        </span>
                                    ))}
                                </div>
                            </>
                        )}
                    </div>

                </div>
            </div>
        );
    }
}

MapTo('goistech/components/pokemon')(Pokemon, PokemonEditConfig);

export default Pokemon;
