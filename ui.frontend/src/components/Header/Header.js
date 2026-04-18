import React, { Component } from 'react';
import { MapTo } from '@adobe/aem-react-editable-components';

const HeaderEditConfig = {
    emptyLabel: 'Header — Gois Tech',
    isEmpty: () => false,
};

class Header extends Component {
    constructor(props) {
        super(props);
        this.state = { menuOpen: false, scrolled: false };
        this.toggleMenu = this.toggleMenu.bind(this);
        this.handleScroll = this.handleScroll.bind(this);
        this.handleKeyDown = this.handleKeyDown.bind(this);
    }

    componentDidMount() {
        window.addEventListener('scroll', this.handleScroll, { passive: true });
        document.addEventListener('keydown', this.handleKeyDown);
    }

    componentWillUnmount() {
        window.removeEventListener('scroll', this.handleScroll);
        document.removeEventListener('keydown', this.handleKeyDown);
    }

    handleScroll() {
        this.setState({ scrolled: window.scrollY > 50 });
    }

    handleKeyDown(e) {
        if (e.key === 'Escape') this.setState({ menuOpen: false });
    }

    toggleMenu() {
        this.setState(prev => ({ menuOpen: !prev.menuOpen }));
    }

    render() {
        const { logoAltText, ctaText, ctaLink } = this.props;
        const { menuOpen, scrolled } = this.state;

        const headerClass = [
            'cmp-header',
            scrolled ? 'cmp-header--scrolled' : '',
        ].filter(Boolean).join(' ');

        return (
            <header className={headerClass} data-cmp-is="header">
                <div className="cmp-header__inner">

                    <a href="/" className="cmp-header__logo" aria-label={logoAltText || 'Gois Tech'}>
                        <span className="cmp-header__logo-icon" aria-hidden="true">GT</span>
                        <span className="cmp-header__logo-text">
                            Gois<strong>Tech</strong>
                        </span>
                    </a>

                    <nav
                        className={`cmp-header__nav${menuOpen ? ' is-open' : ''}`}
                        aria-label="Navegação principal"
                        id="cmp-header-nav"
                    >
                        <ul className="cmp-navigation__group">
                            <li className="cmp-navigation__item">
                                <a className="cmp-navigation__item-link" href="#sobre">Sobre</a>
                            </li>
                            <li className="cmp-navigation__item">
                                <a className="cmp-navigation__item-link" href="#servicos">Serviços</a>
                            </li>
                            <li className="cmp-navigation__item">
                                <a className="cmp-navigation__item-link" href="#cases">Cases</a>
                            </li>
                            <li className="cmp-navigation__item">
                                <a className="cmp-navigation__item-link" href="#blog">Blog</a>
                            </li>
                        </ul>
                    </nav>

                    <button
                        className="cmp-header__menu-toggle"
                        aria-expanded={menuOpen}
                        aria-controls="cmp-header-nav"
                        aria-label={menuOpen ? 'Fechar menu' : 'Abrir menu'}
                        onClick={this.toggleMenu}
                    >
                        <span className="cmp-header__menu-toggle-bar"></span>
                        <span className="cmp-header__menu-toggle-bar"></span>
                        <span className="cmp-header__menu-toggle-bar"></span>
                    </button>

                    <a
                        href={ctaLink || '#contato'}
                        className="cmp-header__cta"
                    >
                        {ctaText || 'Fale Conosco'}
                    </a>

                </div>
            </header>
        );
    }
}

MapTo('goistech/components/header')(Header, HeaderEditConfig);

export default Header;
