package de.mvitz.aoc2023;

import java.util.*;

import static java.util.function.Predicate.not;
import static java.util.stream.Collectors.toSet;

final class Day10 {

	private Day10() {
	}

	static long stepsToReachFarthestPositionFor(String input) {
		var grid = Grid.parse(input);

		var start = grid.start();

		var currentTiles = start.connects().stream()
				.map(grid::tileAt)
				.filter(Optional::isPresent)
				.map(Optional::get)
				.filter(tile -> tile.connects().contains(start.point()))
				.collect(toSet());
		var steps = 1;

		var visitedTiles = new HashSet<>();
		visitedTiles.add(start);

		while (currentTiles.size() != 1) {
			visitedTiles.addAll(currentTiles);
			steps++;
			currentTiles = currentTiles.stream()
					.map(grid::connectingTilesOf)
					.flatMap(Set::stream)
					.filter(not(visitedTiles::contains))
					.collect(toSet());
		}

		return steps;
	}


	record Grid(Map<Point, Tile> tiles) {

		public Tile start() {
			return tiles.values().stream()
					.filter(Tile.Start.class::isInstance)
					.findFirst()
					.orElseThrow();
		}

		public Set<Tile> connectingTilesOf(Tile tile) {
			return tile.connects().stream()
					.map(this::tileAt)
					.filter(Optional::isPresent)
					.map(Optional::get)
					.collect(toSet());
		}

		public Optional<Tile> tileAt(Point point) {
			return Optional.ofNullable(tiles.get(point));
		}

		public static Grid parse(String input) {
			var tiles = new HashMap<Point, Tile>();

			var lines = input.lines().toList();
			for (int y = 0; y < lines.size(); y++) {
				var columns = lines.get(y).split("");
				for (int x = 0; x < columns.length; x++) {
					var point = new Point(x, y);
					tiles.put(point, Tile.of(point, columns[x]));
				}
			}

			return new Grid(tiles);
		}
	}

	public record Point(int x, int y) {
	}

	sealed interface Tile {

		Point point();

		Set<Point> connects();

		static Tile of(Point point, String type) {
			return switch (type) {
				case "|" -> new Tile.VerticalPipe(point);
				case "-" -> new Tile.HorizontalPipe(point);
				case "L" -> new Tile.NorthEastPipe(point);
				case "J" -> new Tile.NorthWestPipe(point);
				case "7" -> new Tile.SouthWestPipe(point);
				case "F" -> new Tile.SouthEastPipe(point);
				case "." -> new Tile.Ground(point);
				case "S" -> new Tile.Start(point);
				default ->
						throw new IllegalArgumentException("Found unknown tile type %s at %s".formatted(type, point));
			};
		}

		record VerticalPipe(Point point) implements Tile {

			@Override
			public Set<Point> connects() {
				return Set.of(
						new Point(point.x, point.y - 1),
						new Point(point.x, point.y + 1));
			}
		}

		record HorizontalPipe(Point point) implements Tile {

			@Override
			public Set<Point> connects() {
				return Set.of(
						new Point(point.x - 1, point.y),
						new Point(point.x + 1, point.y));
			}
		}

		record NorthEastPipe(Point point) implements Tile {

			@Override
			public Set<Point> connects() {
				return Set.of(
						new Point(point.x, point.y - 1),
						new Point(point.x + 1, point.y));
			}
		}

		record NorthWestPipe(Point point) implements Tile {

			@Override
			public Set<Point> connects() {
				return Set.of(
						new Point(point.x, point.y - 1),
						new Point(point.x - 1, point.y));
			}
		}

		record SouthWestPipe(Point point) implements Tile {

			@Override
			public Set<Point> connects() {
				return Set.of(
						new Point(point.x, point.y + 1),
						new Point(point.x - 1, point.y));
			}
		}

		record SouthEastPipe(Point point) implements Tile {

			@Override
			public Set<Point> connects() {
				return Set.of(
						new Point(point.x, point.y + 1),
						new Point(point.x + 1, point.y));
			}
		}

		record Ground(Point point) implements Tile {

			@Override
			public Set<Point> connects() {
				return Set.of();
			}
		}

		record Start(Point point) implements Tile {

			@Override
			public Set<Point> connects() {
				return Set.of(
						new Point(point.x, point.y - 1),
						new Point(point.x + 1, point.y),
						new Point(point.x, point.y + 1),
						new Point(point.x - 1, point.y));
			}
		}
	}
}
